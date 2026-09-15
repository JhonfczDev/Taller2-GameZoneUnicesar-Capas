package co.unicesar.edu.taller2.gamezoneunicesar.capas.persistence;

import java.io.File;

public class ReturnRepository {

    private File file;

    public ReturnRepository() {this("data/returns.csv");}

    public ReturnRepository(String filepath) {
        this.file = new File(filepath);
    }
}
