package ies.accesodatos.commons.repositories;

import java.io.File;
import java.util.stream.Stream;

public abstract class AWriteRepository<T,K>  implements IWriteRepository<T,K>{



    protected String findFileByName(String path, int id) {
        String sid = String.valueOf(id);
        return Stream.of(new File(path).listFiles())
                //se seleccionan los que no sean directorios y contengan el id
                .filter(file -> !file.isDirectory() && file.getName().contains(sid))
                .findFirst().map(File::getName).orElse(null);
    }
}
