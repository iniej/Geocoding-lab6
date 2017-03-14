package com.Iniebiyo;

import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
public class geocoding {

        public static void main(String[] args) throws  Exception {
            Scanner addr = new Scanner(System.in);
            //this program gets the input address from a user and prints out the elevation of that address
            int name;
            String key = null;

            //Create a key file and read the file.
            try (BufferedReader reader = new BufferedReader(new FileReader("key.txt"))){
                key = reader.readLine();
                System.out.println(key);
            }catch (Exception ioe){
                System.out.println("No key file found, or could not read key. Please verify key.txt present");
                System.exit(-1);
            }

            //Enter an address.
            System.out.println("Plese enter an address");
            GeoApiContext context = new GeoApiContext().setApiKey(key);
            String myaddr = addr.nextLine();

            GeocodingResult results[] = GeocodingApi.geocode(context, myaddr).await();
            for(int x = 0;x<results.length;x++){
                System.out.println(x+": "+ results[x].formattedAddress);
            }
            name = addr.nextInt();
            LatLng r = (results[name].geometry.location);

            ElevationResult[] t = ElevationApi.getByPoints(context,r).await();

            if (results.length >= 1){
                ElevationResult Elevation = t[0];
                System.out.println(String.format("The elevation of " + results[name].formattedAddress +
                        " above sea level is %.2f meters.",Elevation.elevation));


            }
        }
    }

