package bf.openburkina.langtechmoore.service;

import bf.openburkina.langtechmoore.domain.Traduction;
import bf.openburkina.langtechmoore.domain.enumeration.TypeTraduction;
import bf.openburkina.langtechmoore.repository.TraductionRepository;
import bf.openburkina.langtechmoore.service.dto.TraductionDTO;
import bf.openburkina.langtechmoore.service.mapper.TraductionMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;

/**
 * Service Implementation for managing {@link Traduction}.
 */
@Service
@Transactional
public class TraductionService {

    private final Logger log = LoggerFactory.getLogger(TraductionService.class);
    @Value("${langtech.moore.folder.windows}")
    private String docPathWindows;

    @Value("${langtech.moore.folder.linux}")
    private String docPathLinux;

    private static final String ENTITY_NAME = "tradution";


    private final TraductionRepository traductionRepository;

    private final TraductionMapper traductionMapper;

    public TraductionService(TraductionRepository traductionRepository, TraductionMapper traductionMapper) {
        this.traductionRepository = traductionRepository;
        this.traductionMapper = traductionMapper;
    }

    /**
     * Save a traduction.
     *
     * @param traductionDTO the entity to save.
     * @return the persisted entity.
     */
    public TraductionDTO save(TraductionDTO traductionDTO) {
        log.debug("Request to save Traduction : {}", traductionDTO);
        Traduction traduction = traductionMapper.toEntity(traductionDTO);
        traduction = traductionRepository.save(traduction);
        return traductionMapper.toDto(traduction);
    }

    /**
     * Update a traduction.
     *
     * @param traductionDTO the entity to save.
     * @return the persisted entity.
     */
    public TraductionDTO update(TraductionDTO traductionDTO) {
        log.debug("Request to save Traduction : {}", traductionDTO);
        Traduction traduction = traductionMapper.toEntity(traductionDTO);
        traduction = traductionRepository.save(traduction);
        return traductionMapper.toDto(traduction);
    }

    /**
     * Partially update a traduction.
     *
     * @param traductionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TraductionDTO> partialUpdate(TraductionDTO traductionDTO) {
        log.debug("Request to partially update Traduction : {}", traductionDTO);

        return traductionRepository
            .findById(traductionDTO.getId())
            .map(existingTraduction -> {
                traductionMapper.partialUpdate(existingTraduction, traductionDTO);

                return existingTraduction;
            })
            .map(traductionRepository::save)
            .map(traductionMapper::toDto);
    }

    /**
     * Get all the traductions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TraductionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Traductions");
        return traductionRepository.findAll(pageable).map(traductionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<TraductionDTO> findAllByCriteria(TraductionDTO traductionDTO,Pageable pageable) {
        log.debug("Request to get all Traductions");
        return traductionRepository.findAllWithCriteria(
            traductionDTO.getLibelle(),
            traductionDTO.getEtat()!=null?traductionDTO.getEtat().name():null,
            traductionDTO.getType()!=null?traductionDTO.getType().name():null,
            traductionDTO.getContenuAudioContentType(),
            traductionDTO.getSourceDonnee()!=null?traductionDTO.getSourceDonnee().getId():null,
            traductionDTO.getUtilisateur()!=null?traductionDTO.getUtilisateur().getId():null,
            traductionDTO.getLangue()!=null?traductionDTO.getLangue().getId():null
            ,pageable).map(traductionMapper::toDto);
    }

    /**
     * Get one traduction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TraductionDTO> findOne(Long id) {
        log.debug("Request to get Traduction : {}", id);
        return traductionRepository.findById(id).map(traductionMapper::toDto);
    }

    /**
     * Delete the traduction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Traduction : {}", id);
        traductionRepository.deleteById(id);
    }

  /*  public  TraductionDTO saveMultimedia(MultipartFile file) throws IOException {
        TraductionDTO traductionDTO=new TraductionDTO();
        traductionDTO.setLibelle(file.getOriginalFilename());
        traductionDTO.setType(TypeTraduction.AUDIO);
        traductionDTO.setContenuAudioContentType(file.getContentType());
        traductionDTO.setContenuAudio(file.getBytes());
        if(traductionDTO.getContenuAudio()!=null){
            log.debug("------byte---------------"+traductionDTO.getContenuAudio().toString());
            log.debug("------byte---------------"+traductionDTO.getLibelle());
            return  saveDocToFolder(traductionDTO);
        }
       return  null;
    }*/
    // TODO: 03/11/2022 Service pour la sauvegarde des fichiers multimedia dans un repertoire

    public TraductionDTO saveMultimedia(TraductionDTO traductionDTO)  {
        if(traductionDTO.getType().equals(TypeTraduction.AUDIO) && traductionDTO.getContenuAudio()!=null){
            log.debug("------byte---------------"+traductionDTO.getContenuAudio().toString());
            log.debug("------byte---------------"+traductionDTO.getLibelle());
            return  saveDocToFolder(traductionDTO);
        } else {
            Traduction traduction=traductionMapper.toEntity(traductionDTO);
            traductionRepository.save(traduction);
        }
        return  null;
    }



    public TraductionDTO saveDocToFolder(TraductionDTO traductionDTO)  {
        String docName=null;
        String idTraduction= null;
        String finalDirectory=null;
        byte[] content= traductionDTO.getContenuAudio();
        String contentType=getDocType(traductionDTO.getContenuAudioContentType());
        String numeroRandom = RandomStringUtils.randomAlphabetic(3);
        docName="tradution"+numeroRandom;
        //docName=docName.replace(" ", "_");
        // TODO: 03/11/2022 controle sur le systeme d'exploitation
        final String keyDirectory = (SystemUtils.IS_OS_LINUX ? docPathLinux : docPathWindows);
        if (SystemUtils.IS_OS_WINDOWS) {
             finalDirectory = keyDirectory.replace("\\", "/");
        } else {
            finalDirectory=keyDirectory;
        }
        traductionDTO.setContenuAudio(null);
        Traduction traduction=traductionRepository.save(traductionMapper.toEntity(traductionDTO));
        docName=docName+traduction.getId().toString();
        log.debug("xontent type*************---"+contentType);
        try {

            if(traduction.getId()!=null){
                idTraduction=traduction.getId().toString();
                File imageDirectory = new File(finalDirectory);
                log.debug("folder-----"+imageDirectory.getParent()+"and "+imageDirectory.getAbsolutePath());

                File clientFolder= new File(imageDirectory.getAbsolutePath(),idTraduction);
                if (!clientFolder.exists()) {
                    clientFolder.mkdir();
                }
                log.debug("+++++++22**"+clientFolder);
                log.debug("****---------Generate file---------------*****");
                //File imageFile =  new File(clientFolder+"/"+imageName+"."+"png");
                File traductionFile =  new File(clientFolder+"/"+docName+"."+contentType);
                String folderToSave=finalDirectory+"/"+ idTraduction+ "/" + docName + "." + contentType;

                log.debug("****---------Generate 2---------------*****");
                traduction.setCheminDocument(folderToSave);
                traductionRepository.save(traduction);
                traductionDTO=traductionMapper.toDto(traduction);
                traductionDTO.setContenuAudio(content);
                BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(traductionFile));
                stream.write(content);
                stream.close();
                return traductionDTO;

            }
            return null;

        } catch (Exception e){
            log.debug("impossible de generer le fichier", e.getMessage());
            log.debug("Message error"+e.getLocalizedMessage());
            log.debug("**************************",e.getCause());
            log.debug("****--------------*********"+e.toString());
            return  null;
        }

    }


    // TODO: 03/11/2022 service de récupération de la traduction avec document déposé sur le repertoire
    public TraductionDTO getTraductionDoc(Long traductionId) throws IOException {
        TraductionDTO traductionDTO=new TraductionDTO();
        log.debug("*---------------"+traductionId);
        String traductionFolder=null;
        byte[] bytes;
        if(traductionId!=null){
            Traduction traduction =traductionRepository.findByTraductionId(traductionId);
            traductionDTO=traductionMapper.toDto(traduction);
            if(traduction.getId()!=null && traduction.getType().equals(TypeTraduction.AUDIO)){
                traductionFolder=traduction.getCheminDocument();
                File img = new File(traductionFolder);
                log.debug("is file------"+img+"et bool"+img.isFile());
                // bytes = FileUtils.readFileToByteArray(img);
                  bytes = Files.readAllBytes(Path.of(img.getPath()));
                traductionDTO.setContenuAudio(bytes);
            }

        }
        return traductionDTO;
    }

    public File convert(MultipartFile file) throws IOException
    {

        File convFile = new File("./IN_FILE/"+file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();

        return convFile ;
    }

    public String getDocType(String contentType){
        String  ft ="png";
        if(contentType.equals("image/png")){
            ft= "png";
        } else if(contentType.equals("image/jpeg")){
            ft= "jpeg";
        } else if(contentType.equals("image/jpg")){
            ft= "jpg";
        } else if(contentType.equals("audio/mpeg")){
            ft="mpeg";
        }else if(contentType.equals("audio/mp3")){
            ft="mp3";
        }else if(contentType.equals("audio/mp4")){
            ft="mp4";
        }
        else {
            ft="png";
        }
        return ft;
    }
}
