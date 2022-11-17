package bf.openburkina.langtechmoore.web.rest;

import bf.openburkina.langtechmoore.domain.Authority;
import bf.openburkina.langtechmoore.domain.Profil;
import bf.openburkina.langtechmoore.domain.Utilisateur;
import bf.openburkina.langtechmoore.repository.ProfilRepository;
import bf.openburkina.langtechmoore.repository.UtilisateurRepository;
import bf.openburkina.langtechmoore.security.jwt.JWTFilter;
import bf.openburkina.langtechmoore.security.jwt.TokenProvider;
import bf.openburkina.langtechmoore.service.UserService;
import bf.openburkina.langtechmoore.service.dto.UserDTO;
import bf.openburkina.langtechmoore.web.rest.vm.LoginVM;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserService userService;

    private final UtilisateurRepository utilisateurRepository;

    private final ProfilRepository profilRepository;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService, UtilisateurRepository utilisateurRepository, ProfilRepository profilRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
        this.utilisateurRepository = utilisateurRepository;
        this.profilRepository = profilRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        UserDTO adminUserDTO = userService
            .getUserWithAuthoritiesByLogin(loginVM.getUsername())
            .map(UserDTO::new)
            .orElseThrow(() -> new AccountResourceException("User could not be found"));
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByUserId(adminUserDTO.getId());
        if (utilisateur.isPresent()) {
            if (utilisateur.get().getProfil()!=null){
                Profil profil = profilRepository.findOneWithEagerRelationships(utilisateur.get().getProfil().getId());
                utilisateur.get().getProfil().setRoles(profil.getRoles().stream().collect(Collectors.toSet()));
            }
            log.debug(utilisateur.get().toString());
        }
        return new ResponseEntity<>(new JWTToken(jwt,utilisateur.get()), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        private Utilisateur utilisateur;

        JWTToken(String idToken,Utilisateur utilisateur) {
            this.idToken = idToken;
            this.utilisateur = utilisateur;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }

        public Utilisateur getUtilisateur() {
            return utilisateur;
        }

        public void setUtilisateur(Utilisateur utilisateur) {
            this.utilisateur = utilisateur;
        }
    }

    private static class AccountResourceException extends RuntimeException {

        private AccountResourceException(String message) {
            super(message);
        }
    }
}
