package ru.zaoemtika.EmtikaWork;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

class MyFileFindVisitor1 extends SimpleFileVisitor<Path> { 
    private PathMatcher matcher; 
 
    public MyFileFindVisitor1(String pattern) { 
        try { 
            matcher = FileSystems.getDefault().getPathMatcher(pattern); 
        } catch (IllegalArgumentException iae) { 
            System.err 
                    .println("Invalid pattern; did you forget to prefix \"glob:\" or \"regex:\"?"); 
            System.exit(1); 
        } 
    } 
 
    public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) { 
        find(path); 
        return FileVisitResult.CONTINUE; 
    } 
 
    private void find(Path path) { 
        Path name = path.getFileName(); 
        if (matcher.matches(name)) 
        	AllWork.setListAllSuffix(path.getFileName().toString().toLowerCase());
    } 
} 
 
public class GetAllSuffixFile { 
    public static void getAllSuffixFile(String pathSource, String suffix) { 
        Path startPath = Paths.get(pathSource); 
        String pattern = "glob:*." + suffix; 
        try { 
            Files.walkFileTree(startPath, new MyFileFindVisitor1(pattern)); 
        } catch (IOException e) { 
        	AllWork.setErrorsCount(AllWork.getErrorsCount() + 1);
            e.printStackTrace(); 
        } 
    } 
} 