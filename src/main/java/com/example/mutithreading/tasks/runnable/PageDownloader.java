package com.example.mutithreading.tasks.runnable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class PageDownloader implements Runnable {

    private List<String> urls = new ArrayList<>();

    @Override
    public void run() {
        try {
            for (String urlString : urls) {
                URL url = new URL(urlString);
                String fileName = urlString.substring(urlString.lastIndexOf("/") + 1).trim() + ".html";
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                }
                log.info("Page is downloaded to {}", fileName);
                writer.close();
                reader.close();
            }
        } catch (Exception ex) {
            log.error("An error occurred while processing page");
            ex.printStackTrace();
        }
    }
}
