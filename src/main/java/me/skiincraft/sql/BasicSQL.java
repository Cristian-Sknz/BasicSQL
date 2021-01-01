package me.skiincraft.sql;

import me.skiincraft.sql.exceptions.RepositoryException;
import me.skiincraft.sql.platform.SQLPlatform;
import me.skiincraft.sql.repository.Repository;
import me.skiincraft.sql.repository.BasicRepository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasicSQL {

    private static SQLPlatform sql;
    private static BasicSQL instance;

    private BasicSQL() {
    }

    public <E extends Repository<T, ID>, T, ID> Repository<T, ID> registerRepository(Class<E> repository) throws RepositoryException {
        List<Type> types = new ArrayList<>();
        for (Type genericInterface : repository.getGenericInterfaces()) {
            if (genericInterface instanceof ParameterizedType) {
                Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                types.add(genericTypes[0]);
                types.add(genericTypes[1]);
            }
        }

        return new BasicRepository<T, ID>((Class<T>) types.get(0), (Class<ID>) types.get(1)) {};
    }


    public static BasicSQL create(SQLPlatform platform) throws InstantiationException, SQLException {
        if (sql != null)
            throw new InstantiationException("Essa classe já foi instanciada!");

        sql = platform;
        sql.connect();
        return instance = new BasicSQL();
    }

    public static BasicSQL getInstance(){
        return instance;
    }

    public static SQLPlatform getSQL() {
        return sql;
    }
}
