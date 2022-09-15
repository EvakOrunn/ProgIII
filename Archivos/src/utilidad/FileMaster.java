package utilidad;

public class FileMaster {

	private RandomAccessFile master;
	private File maker;
	private File arch;

	public static void crearFicheroBinario(){
		try {
			if (SystemQuery.isWindows()) {
				maker = new File("C:\\Archivo");
				if (maker.mkdirs()) {
					arch = File(maker.getPath());
				}
			} else {
				if (SystemQuery.isUnix()) {
					String userHome = SystemQuery.getUser();
					maker = new File("/home/" + userHome + "/Archivo");
					if (maker.mkdirs()) {
						arch = new File(maker.getPath());
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error:" + e.getMessage());
		}
		
	}

}