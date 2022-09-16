package SIOPE.SIOPE.Controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ListaAckFlussoOrdinativi.ListaAckFlussoOrdinativi;
import ListaAckFlussoOrdinativi.infoAck;
import ListaDisponibilitaLiquide.ListaDisponibilitaLiquide;
import ListaDisponibilitaLiquide.infoDisponibilita;
import ListaEsitiFlussoOrdinativi.ListaEsitiFlussoOrdinativi;
import ListaEsitiFlussoOrdinativi.infoEsiti;
import ListaGiornaliDiCassa.ListaGiornaliDiCassa;
import ListaGiornaliDiCassa.infoGiornali;
import ListaMessaggiEsitoApplicativo.ListaMessaggiEsitoApplicativo;
import ListaMessaggiEsitoApplicativo.infoEsitoApplicativo;
import UploadFlussoOrdinativi.UploadFlussoOrdinativi;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;


@RestController
public class SiopeController{
	
	@RequestMapping(value = "v1/{idA2A}/PA/{codEnte}/flusso/",
					method = RequestMethod.POST,
					produces = "application/json;charset=UTF-8",
					consumes= "application/zip" )
	public UploadFlussoOrdinativi  UploadFlussoOrdinativo (@PathVariable("idA2A") String idA2A,
														   @PathVariable("codEnte") Integer codEnte,
														   @RequestBody byte[] request, 
														   HttpServletResponse response) throws Exception {
		
		
		HttpEntity<byte[]> r = new HttpEntity<byte[]>(request); 
		//fare controlli su request.getHeader();
		
		 String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("v1/{idA2A}/PA/{codEnte}/flusso/{progFlusso}/").path("casa").toUriString();
		 
		 createZipFile(r.getBody());
		
		 //setto header
		 response.addDateHeader("Date", Timestamp.valueOf(getDate()).getTime());
		 response.setContentType("application/json;charset=UTF-8");
		 response.setHeader("location",fileDownloadUri);
		 
		return new UploadFlussoOrdinativi(new Random().nextInt(1000000000),getDate(),false,fileDownloadUri) ;
	}

	@RequestMapping( value = {"v1/{idA2A}/PA/{codEnte}/flusso/ack/",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/download={download}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/download={download}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/dataProduzioneA={dataProduzioneA}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/dataProduzioneA={dataProduzioneA}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/dataProduzioneA={dataProduzioneA}&download={download}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/dataProduzioneA={dataProduzioneA}&download={download}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/dataProduzioneDa={dataProduzioneDa}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/dataProduzioneDa={dataProduzioneDa}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/dataProduzioneDa={dataProduzioneDa}&download={download}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/dataProduzioneDa={dataProduzioneDa}&download={download}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/dataProduzioneDa={dataProduzioneDa}&dataProduzioneA={dataProduzioneA}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/dataProduzioneDa={dataProduzioneDa}&dataProduzioneA={dataProduzioneA}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/dataProduzioneDa={dataProduzioneDa}&dataProduzioneA={dataProduzioneA}&download={download}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/ack/dataProduzioneDa={dataProduzioneDa}&dataProduzioneA={dataProduzioneA}&download={download}&pagina={pagina}"
							 },
					 method = RequestMethod.GET,
					 produces = "application/json;charset=UTF-8")
	
	
	public ListaAckFlussoOrdinativi ListaAckFlussoOrdinativi(@PathVariable("idA2A") String idA2A,
															 @PathVariable("codEnte") Integer codEnte,
															 @PathVariable(required = false) String dataProduzioneDa,
															 @PathVariable(required = false) String dataProduzioneA,
															 @PathVariable(required = false) String download,
															 @PathVariable(required = false) Integer pagina,
															 HttpServletResponse response
															) {
	
		response.setContentType("application/json;charset=UTF-8");
		response.addDateHeader("Date", Timestamp.valueOf(getDate()).getTime());
		response.setStatus(HttpServletResponse.SC_OK);
		
		//dati di esempio
		List<infoAck> l = new	 ArrayList<infoAck>();
		l.add(new infoAck(124567892,"2016-12-09T11:22:34.000",false,"rhwrretjhrg"));
		l.add(new  infoAck(1234567893,"2016-12-09T22:33:44.000",false,"herjrty"));
		return new ListaAckFlussoOrdinativi (12345, 124, 100, 1,
											 "2016-12-09T11:22:33.444",
											 "2016-12-09T22:33:44.555", l);
	}

	@RequestMapping( value = {"v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/download={download}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/download={download}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/dataUploadA={dataUploadA}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/dataUploadA={dataUploadA}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/dataUploadA={dataUploadA}&download={download}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/dataUploadA={dataUploadA}&download={download}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/dataUploadDa={dataUploadDa}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/dataUploadDa={dataUploadDa}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/dataUploadDa={dataUploadDa}&download={download}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/dataUploadDa={dataUploadDa}&download={download}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}&download={download}",
							  "v1/{idA2A}/PA/{codEnte}/flusso/esitoflusso/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}&download={download}&pagina={pagina}"
			 				  },
				method = RequestMethod.GET,
				produces = "application/json;charset=UTF-8")
	public ListaEsitiFlussoOrdinativi ListaEsitiFlussoOrdinativi(@PathVariable("idA2A") String idA2A,
																 @PathVariable("codEnte") Integer codEnte,
																 @PathVariable(required = false) String dataUploadDa,
																 @PathVariable(required = false) String dataUploadA,
																 @PathVariable(required = false) String download,
																 @PathVariable(required = false) Integer pagina,
																 HttpServletResponse response
																) {
			
			response.setContentType("application/json;charset=UTF-8");
			response.addDateHeader("Date", Timestamp.valueOf(getDate()).getTime());
			response.setStatus(HttpServletResponse.SC_OK);
		
			//dati di esempio
			List<infoEsiti> l = new	 ArrayList<infoEsiti>();
			l.add(new infoEsiti(124567892,"2016-12-09T11:22:34.000",false,"rhwrretjhrg"));
			l.add(new  infoEsiti(1234567893,"2016-12-09T22:33:44.000",false,"herjrty"));
			return new ListaEsitiFlussoOrdinativi (12345, 124, 100, 1,
													"2016-12-09T11:22:33.444",
													"2016-12-09T22:33:44.555", l);
	}

	@RequestMapping( value = {"v1/{idA2A}/PA/{codEnte}/esitoapplicativo/",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/pagina={pagina}",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/download={download}",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/download={download}&pagina={pagina}",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/dataUploadA={dataUploadA}",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/dataUploadA={dataUploadA}&pagina={pagina}",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/dataUploadA={dataUploadA}&download={download}",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/dataUploadA={dataUploadA}&download={download}&pagina={pagina}",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/dataUploadDa={dataUploadDa}",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/dataUploadDa={dataUploadDa}&pagina={pagina}",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/dataUploadDa={dataUploadDa}&download={download}",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/dataUploadDa={dataUploadDa}&download={download}&pagina={pagina}",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}&pagina={pagina}",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}&download={download}",
			  				  "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}&download={download}&pagina={pagina}"
							  },
					method = RequestMethod.GET,
					produces = "application/json;charset=UTF-8")
	public ListaMessaggiEsitoApplicativo ListaMessaggiEsitoApplicativo(@PathVariable("idA2A") String idA2A,
												 					   @PathVariable("codEnte") Integer codEnte,
												 					   @PathVariable(required = false) String dataUploadDa,
												 					   @PathVariable(required = false) String dataUploadA,
												 					   @PathVariable(required = false) String download,
												 					   @PathVariable(required = false) Integer pagina,
												 					   HttpServletResponse response
																	   ) {

			response.setContentType("application/json;charset=UTF-8");
			response.addDateHeader("Date", Timestamp.valueOf(getDate()).getTime());
			response.setStatus(HttpServletResponse.SC_OK);

			//dati di esempio
			List<infoEsitoApplicativo> l = new	 ArrayList<infoEsitoApplicativo>();
			l.add(new  infoEsitoApplicativo(124567892,"2016-12-09T11:22:34.000",false,"rhwrretjhrg"));
			l.add(new  infoEsitoApplicativo(1234567893,"2016-12-09T22:33:44.000",false,"herjrty"));
			return new ListaMessaggiEsitoApplicativo (12345, 124, 100, 1,
													"2016-12-09T11:22:33.444",
													"2016-12-09T22:33:44.555", l);
	}

	@RequestMapping( value = {"v1/{idA2A}/PA/{codEnte}/giornale/",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/pagina={pagina}",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/download={download}",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/download={download}&pagina={pagina}",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/dataUploadA={dataUploadA}",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/dataUploadA={dataUploadA}&pagina={pagina}",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/dataUploadA={dataUploadA}&download={download}",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/dataUploadA={dataUploadA}&download={download}&pagina={pagina}",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/dataUploadDa={dataUploadDa}",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/dataUploadDa={dataUploadDa}&pagina={pagina}",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/dataUploadDa={dataUploadDa}&download={download}",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/dataUploadDa={dataUploadDa}&download={download}&pagina={pagina}",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}&pagina={pagina}",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}&download={download}",
			  				  "v1/{idA2A}/PA/{codEnte}/giornale/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}&download={download}&pagina={pagina}"
			  					},
					method = RequestMethod.GET,
					produces = "application/json;charset=UTF-8")
	public ListaGiornaliDiCassa ListaGiornaliDiCassa(@PathVariable("idA2A") String idA2A,
								 				 @PathVariable("codEnte") Integer codEnte,
								 		    	 @PathVariable(required = false) String dataUploadDa,
								 			     @PathVariable(required = false) String dataUploadA,
								 			     @PathVariable(required = false) String download,
								 		         @PathVariable(required = false) Integer pagina,
								 				 HttpServletResponse response
											     ) {

		response.setContentType("application/json;charset=UTF-8");
		response.addDateHeader("Date", Timestamp.valueOf(getDate()).getTime());
		response.setStatus(HttpServletResponse.SC_OK);

		//dati di esempio
		List<infoGiornali> l = new	 ArrayList<infoGiornali>();
		l.add(new  infoGiornali(124567892,"2016-12-09T11:22:34.000",false,"rhwrretjhrg"));
		l.add(new  infoGiornali(1234567893,"2016-12-09T22:33:44.000",false,"herjrty"));
		return new ListaGiornaliDiCassa (12345, 124, 100, 1,
										"2016-12-09T11:22:33.444",
										"2016-12-09T22:33:44.555", l);
	}
	
	@RequestMapping( value = {"v1/{idA2A}/PA/{codEnte}/disponibilita/",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/download={download}",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/download={download}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/dataUploadA={dataUploadA}",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/dataUploadA={dataUploadA}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/dataUploadA={dataUploadA}&download={download}",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/dataUploadA={dataUploadA}&download={download}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/dataUploadDa={dataUploadDa}",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/dataUploadDa={dataUploadDa}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/dataUploadDa={dataUploadDa}&download={download}",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/dataUploadDa={dataUploadDa}&download={download}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}&pagina={pagina}",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}&download={download}",
							  "v1/{idA2A}/PA/{codEnte}/disponibilita/dataUploadDa={dataUploadDa}&dataUploadA={dataUploadA}&download={download}&pagina={pagina}"
								},
					method = RequestMethod.GET,
					produces = "application/json;charset=UTF-8")
	public ListaDisponibilitaLiquide ListaDisponibilitaLiquide(@PathVariable("idA2A") String idA2A,
				 				 						   @PathVariable("codEnte") Integer codEnte,
				 				 						   @PathVariable(required = false) String dataUploadDa,
				 				 						   @PathVariable(required = false) String dataUploadA,
				 				 						   @PathVariable(required = false) String download,
				 				 						   @PathVariable(required = false) Integer pagina,
				 				 						   HttpServletResponse response
															) {

		response.setContentType("application/json;charset=UTF-8");
		response.addDateHeader("Date", Timestamp.valueOf(getDate()).getTime());
		response.setStatus(HttpServletResponse.SC_OK);

		//dati di esempio
		List<infoDisponibilita> l = new	 ArrayList<infoDisponibilita>();
		l.add(new  infoDisponibilita(124567892,"2016-12-09T11:22:34.000",false,"rhwrretjhrg"));
		l.add(new  infoDisponibilita(1234567893,"2016-12-09T22:33:44.000",false,"herjrty"));
		return new ListaDisponibilitaLiquide (12345, 124, 100, 1,
											  "2016-12-09 11:22:33.444",
											  "2016-12-09 22:33:44.555", l);
		}

	
	@RequestMapping(value = "v1/{idA2A}/PA/{codEnte}/flusso/{progFlusso}/ack",
					method = RequestMethod.GET, produces="application/zip")
	public byte[] DownloadAckFlussoOrdinativi(@PathVariable("idA2A") String idA2A,
	 								   @PathVariable("codEnte") Integer codEnte,
	 								   @PathVariable("progFlusso") Integer progFlusso,
									   HttpServletResponse response
									  ) throws IOException{
    new Timestamp(10000000);

	//setting headers
	response.addDateHeader("Date", Timestamp.valueOf(getDate()).getTime());
    response.setStatus(HttpServletResponse.SC_OK);
    response.addHeader("Content-Disposition", "form-data; name=\"attachment\"; filename=\"flusso_"+ progFlusso + "_ack.zip\"");
    response.setContentType("application/zip");
    response.setContentLength(10000000);
    
    //creating byteArray stream, make it bufforable and passing this buffor to ZipOutputStream
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
    ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

    //simple file list, just for tests
    ArrayList<File> files = new ArrayList<>(2);
    File r = new File("flusso_opi_importato_18517502.xml");
    files.add(r);

    //packing files
    for (File file : files) {
        //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
        zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
        FileInputStream fileInputStream = new FileInputStream(file);

        IOUtils.copy(fileInputStream, zipOutputStream);

        fileInputStream.close();
        zipOutputStream.closeEntry();
    }

    if (zipOutputStream != null) {
        zipOutputStream.finish();
        zipOutputStream.flush();
        IOUtils.closeQuietly(zipOutputStream);
    }
    IOUtils.closeQuietly(bufferedOutputStream);
    IOUtils.closeQuietly(byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
}

	@RequestMapping(value = "v1/{idA2A}/PA/{codEnte}/flusso/{progFlusso}/esitoflusso",
			method = RequestMethod.GET, produces="application/zip")
    public byte[] DownloadEsitoFlussoOrdinativi(@PathVariable("idA2A") String idA2A,
	 								   @PathVariable("codEnte") Integer codEnte,
	 								   @PathVariable("progFlusso") Integer progFlusso,
									   HttpServletResponse response
									  ) throws IOException{
    new Timestamp(10000000);
    
	//setting headers
	response.addDateHeader("Date", Timestamp.valueOf(getDate()).getTime());
    response.setStatus(HttpServletResponse.SC_OK);
    response.addHeader("Content-Disposition", "form-data; name=\"attachment\"; filename=\"flusso_"+ progFlusso + "_esito.zip\"");
    response.setContentType("application/zip");
    response.setContentLength(10000000);
    
    //creating byteArray stream, make it bufforable and passing this buffor to ZipOutputStream
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
    ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

    //simple file list, just for tests
    ArrayList<File> files = new ArrayList<>(2);
    File r = new File("flusso_opi_importato_18517502.xml");
    files.add(r);

    //packing files
    for (File file : files) {
        //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
        zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
        FileInputStream fileInputStream = new FileInputStream(file);

        IOUtils.copy(fileInputStream, zipOutputStream);

        fileInputStream.close();
        zipOutputStream.closeEntry();
    }

    if (zipOutputStream != null) {
        zipOutputStream.finish();
        zipOutputStream.flush();
        IOUtils.closeQuietly(zipOutputStream);
    }
    IOUtils.closeQuietly(bufferedOutputStream);
    IOUtils.closeQuietly(byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
}
	
	@RequestMapping(value = "v1/{idA2A}/PA/{codEnte}/esitoapplicativo/{progEsitoApllicativo}",
			method = RequestMethod.GET, produces="application/zip")
    public byte[] DownloadMessaggioEsitoApplicativo(@PathVariable("idA2A") String idA2A,
	 								   				@PathVariable("codEnte") Integer codEnte,
	 								   				@PathVariable("progEsitoApllicativo") Integer progEsitoApllicativo,
	 								   				HttpServletResponse response
    												) throws IOException{
    new Timestamp(10000000);
    
	//setting headers
	response.addDateHeader("Date", Timestamp.valueOf(getDate()).getTime());
    response.setStatus(HttpServletResponse.SC_OK);
    response.addHeader("Content-Disposition", "form-data; name=\"attachment\"; filename=\"esitoapplicativo_"+ progEsitoApllicativo + ".zip\"");
    response.setContentType("application/zip");
    response.setContentLength(10000000);
    
    //creating byteArray stream, make it bufforable and passing this buffor to ZipOutputStream
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
    ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

    //simple file list, just for tests
    ArrayList<File> files = new ArrayList<>(2);
    File r = new File("flusso_opi_importato_18517502.xml");
    files.add(r);

    //packing files
    for (File file : files) {
        //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
        zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
        FileInputStream fileInputStream = new FileInputStream(file);

        IOUtils.copy(fileInputStream, zipOutputStream);

        fileInputStream.close();
        zipOutputStream.closeEntry();
    }

    if (zipOutputStream != null) {
        zipOutputStream.finish();
        zipOutputStream.flush();
        IOUtils.closeQuietly(zipOutputStream);
    }
    IOUtils.closeQuietly(bufferedOutputStream);
    IOUtils.closeQuietly(byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
}
	
	@RequestMapping(value = "v1/{idA2A}/PA/{codEnte}/giornale/{progGiornale}",
			method = RequestMethod.GET, produces="application/zip")
    public byte[] DownloadGiornaleDiCassa(@PathVariable("idA2A") String idA2A,
	 								   	  @PathVariable("codEnte") Integer codEnte,
	 								   	  @PathVariable("progGiornale") Integer progGiornale,
	 								   	  HttpServletResponse response
    									  ) throws IOException{
    new Timestamp(10000000);
    
	//setting headers
	response.addDateHeader("Date", Timestamp.valueOf(getDate()).getTime());
    response.setStatus(HttpServletResponse.SC_OK);
    response.addHeader("Content-Disposition", "form-data; name=\"attachment\"; filename=\"giornale_"+ progGiornale + ".zip\"");
    response.setContentType("application/zip");
    response.setContentLength(10000000);
    
    //creating byteArray stream, make it bufforable and passing this buffor to ZipOutputStream
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
    ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

    //simple file list, just for tests
    ArrayList<File> files = new ArrayList<>(2);
    File r = new File("flusso_opi_importato_18517502.xml");
    files.add(r);

    //packing files
    for (File file : files) {
        //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
        zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
        FileInputStream fileInputStream = new FileInputStream(file);

        IOUtils.copy(fileInputStream, zipOutputStream);

        fileInputStream.close();
        zipOutputStream.closeEntry();
    }

    if (zipOutputStream != null) {
        zipOutputStream.finish();
        zipOutputStream.flush();
        IOUtils.closeQuietly(zipOutputStream);
    }
    IOUtils.closeQuietly(bufferedOutputStream);
    IOUtils.closeQuietly(byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
}
	
	@RequestMapping(value = "v1/{idA2A}/PA/{codEnte}/disponibilita/{progDisponibilita}",
				    method = RequestMethod.GET, produces="application/zip")
	public byte[] DownloadDisponibilitaLiquide(@PathVariable("idA2A") String idA2A,
	 								   	  	   @PathVariable("codEnte") Integer codEnte,
	 								   	  	   @PathVariable("progDisponibilita") Integer progDisponibilita,
	 								   	  	   HttpServletResponse response
    									  		) throws IOException{
    new Timestamp(10000000);
    
	//setting headers
	response.addDateHeader("Date", Timestamp.valueOf(getDate()).getTime());
    response.setStatus(HttpServletResponse.SC_OK);
    response.addHeader("Content-Disposition", "form-data; name=\"attachment\"; filename=\"disponibilita_"+ progDisponibilita + ".zip\"");
    response.setContentType("application/zip");
    response.setContentLength(10000000);
    
    //creating byteArray stream, make it bufforable and passing this buffor to ZipOutputStream
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
    ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

    //simple file list, just for tests
    ArrayList<File> files = new ArrayList<>(2);
    File r = new File("flusso_opi_importato_18517502.xml");
    files.add(r);

    //packing files
    for (File file : files) {
        //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
        zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
        FileInputStream fileInputStream = new FileInputStream(file);

        IOUtils.copy(fileInputStream, zipOutputStream);

        fileInputStream.close();
        zipOutputStream.closeEntry();
    }

    if (zipOutputStream != null) {
        zipOutputStream.finish();
        zipOutputStream.flush();
        IOUtils.closeQuietly(zipOutputStream);
    }
    IOUtils.closeQuietly(bufferedOutputStream);
    IOUtils.closeQuietly(byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
}
	
	
	//funzioni ausiliarie
	private String getDate() {
		  Calendar calendar = new GregorianCalendar();
		  int ore = calendar.get(Calendar.HOUR)+1;
		  int minuti = calendar.get(Calendar.MINUTE)+1;
		  int secondi = calendar.get(Calendar.SECOND);
		  int millis = calendar.get(Calendar.MILLISECOND);
		  int giorno = calendar.get(Calendar.DAY_OF_MONTH);
		  int mese = calendar.get(Calendar.MONTH)+1;
		  int anno = calendar.get(Calendar.YEAR);
		  return anno + "-" + mese + "-" + giorno + " " + ore +
				 ":" + minuti + ":" + secondi + "." + millis;
	}
	  
	public static void createZipFile(byte[] request) throws IOException {
			
			File f = new File("upload.zip");
			f.createNewFile();
			FileOutputStream fis = new FileOutputStream(f);
			fis.write(request);
			fis.flush();
			fis.close();
		}

	public static String[] creaOpzioni() {
		
		String pref= "v1/{idA2A}/PA/{codEnte}/flusso/ack/";
		String dPD = "dataUploadDa={dataProduzioneDa}";
		String dPA = "&dataUploadA={dataProduzioneA}";
		String dow = "&download={download}";
		String pag = "&pagina={pagina}";
		String[] list = new String[16];
		int j = 0;
		for(int i = 0 ; i < 16; i++ ) {
			
			String str = pref;
	
			if( i > 7) str = str + dPD;
			
			if((i >3 && i < 8) || (i > 11 ) ) {
				if(i >3 && i < 8)
					str = str + dPA.substring(1);
				else 
					str = str + dPA;
			}
			
			if(j%2 == 1) {
				if(i == 2 || i == 3)
					str = str + dow.substring(1);
				else 
					str = str + dow;
			}
			
			if(i%2 == 1) {
				if(i == 1)
					str = str + pag.substring(1);
				else 
					str = str + pag;
			}
			
			if(i%2 == 1) j++;
			
			list[i] = str;;
			System.out.println(str);
		}
		
		return list;
	}
}
