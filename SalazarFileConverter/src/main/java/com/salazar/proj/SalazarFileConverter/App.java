package com.salazar.proj.SalazarFileConverter;

import java.io.IOException;

import com.salazar.proj.io.DSLtoJSONConverter;


public class App 
{
    public static void main( String[] args ) throws IOException
    {
      System.out.println("Conversion started");
      DSLtoJSONConverter converter = new DSLtoJSONConverter();
      converter.write(args[1],  converter.convert(args[0]));
      System.out.println("Conversion completed");
      
    }
}
