

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import net.sourceforge.plantuml.FileUtils;
import net.sourceforge.plantuml.GeneratedImage;
import net.sourceforge.plantuml.SourceFileReader;
import net.sourceforge.plantuml.SourceStringReader;

public class GenerateUML {
	
	public void uml(File grammar)
	{
	//	String str = FileUtils.readFileToString(grammar);
		
        OutputStream png = null;
        try {
            png = new FileOutputStream("uml.jpeg");
            System.out.println("Package");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
            SourceFileReader reader;
			try {
				reader = new SourceFileReader(grammar);
				List<GeneratedImage> l=reader.getGeneratedImages();
				File draw_png=l.get(0).getPngFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        // Write the first image to "png"
            
       
        // Return a null string if no generation
    }

	

}
