package snippet;

public class Snippet {
	public static void main(String[] args) {
			SpringApplication.run(ClientApplication.class, args);
			
			RestTemplate restTemplate = new RestTemplate();
			hellomex response1 = restTemplate.getForObject("http://localhost:8080/v1/101/PA/103/get/", hellomex.class);
			/*File file = new File("/path/to/the/file.txt");
			Path path = Paths.get("/path/to/the/file.txt"); 
			String name = "file.txt"; 
			String originalFileName = "file.txt"; 
			String contentType = "text/plain"; 
			byte[] content = null; 
			try { 
			    content = Files.readAllBytes(path); 
			} catch (final IOException e) { 
			} 
			MultipartFile result = new MockMultipartFile(name,originalFileName, contentType, content);
			*/
			File file = new File("/path/to/file"); 
			FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile()); 
	
			try { 
			    InputStream input = new FileInputStream(file); 
			    OutputStream os = fileItem.getOutputStream(); 
			    IOUtils.copy(input, os); 
			} catch (IOException ex) { 
			    // do something. 
			} 
	
			MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
			stampapost response2 = restTemplate.postForObject("http://localhost:8080/v1/101/PA/103/flusso/",result, stampapost.class);
			System.out.println(response2.toString());
			System.out.println(response1.toString());
		}
	
	}
	
	/*
	.   ____          _            __ _ _						
	/\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
	( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
	\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
	'  |____| .__|_| |_|_| |_\__, | / / / /
	=========|_|==============|___/=/_/_/_/					
	
	*/
}

