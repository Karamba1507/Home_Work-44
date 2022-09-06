package FileService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kz.attractor.java.lesson44.SampleDataModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileServiceUser {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Path path = Paths.get("./user.json");

    private void readString() {
        String json;
        try {
            json = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        gson.fromJson(json, new TypeToken<List<SampleDataModel.User>>() {
        }.getType());
    }

    public static void main(String[] args) {
        FileServiceUser fs = new FileServiceUser();
        fs.readString();
    }
}
