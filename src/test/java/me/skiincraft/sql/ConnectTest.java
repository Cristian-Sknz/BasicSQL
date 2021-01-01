package me.skiincraft.sql;

import me.skiincraft.sql.exceptions.RepositoryException;
import me.skiincraft.sql.model.Produto;
import me.skiincraft.sql.model.ProdutoType;
import me.skiincraft.sql.platform.SQLConfiguration;
import me.skiincraft.sql.platform.template.PostgreSQL;
import me.skiincraft.sql.repository.ProdutoRepository;
import me.skiincraft.sql.repository.Repository;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.EnumMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
public class ConnectTest {

    private static Repository<Produto, Long> produtoRepository;
    private static final AtomicInteger produtoType = new AtomicInteger(0);
    private static final EnumMap<ProdutoType, String> produtos = new EnumMap<>(ProdutoType.class);

    @BeforeAll
    static void beforeAll() {
        produtos.put(ProdutoType.Bolo, "Bolo de Cenoura");
        produtos.put(ProdutoType.Torta, "Torta de Morango");
        produtos.put(ProdutoType.Docinho, "Croissant");
    }

    @Test
    @DisplayName("Verificar se está conectando.")
    @Order(1)
    public void connect() throws InstantiationException, SQLException {
        BasicSQL.create(new PostgreSQL(SQLConfiguration.getPostgresConfig()
                .setUser("postgres")
                .setPassword("123")));
    }

    @Test
    @DisplayName("Criando repositório de produtos.")
    @Order(2)
    public void createRepository() throws RepositoryException {
        Repository<Produto, Long> repository = BasicSQL.getInstance().registerRepository(ProdutoRepository.class);
        System.out.printf("Contém %s produtos no repositório%n", repository.size());

        produtoRepository = repository;
    }

    @RepeatedTest(3)
    @Order(3)
    @DisplayName("Criando um produto e guardando no repositório")
    public void createItem(){
        Repository<Produto, Long> repository = produtoRepository;
        long id = new Random().nextInt(12);
        Produto produto = new Produto();
        ProdutoType type = ProdutoType.values()[produtoType.getAndIncrement()];
        produto.setId(id);
        produto.setNome(produtos.get(type));
        produto.setType(type);
        produto.setQuantidade(1);
        produto.setValor(75.50F);
        produto.setUpdateDate(OffsetDateTime.now(ZoneOffset.UTC));
        repository.save(produto);

        System.out.printf("%s foi criado e está com o Id %s%n", produto.getNome(), produto.getId());
    }

    @Test
    @Order(4)
    @DisplayName("Removendo o primeiro item do repositório.")
    public void removeItem(){
        Repository<Produto, Long> repository = produtoRepository;
        Produto produto = repository.get(0).get();

        System.out.printf("Removendo o produto: '%s'%n", produto.getNome());
        repository.remove(produto);
        System.out.printf("%s foi removido com sucesso.%n", produto.getNome());
        System.out.printf("Contem %s Produtos restantes.%n", repository.size());
    }

    @Test
    @Order(5)
    @DisplayName("Fechando a conexão com o SQL (Postgres)")
    public void closeConnection() throws SQLException {
        System.out.printf("A conexão está %s%n", (BasicSQL.getSQL().isClosed()) ? "fechada" : "aberta");
        if (BasicSQL.getSQL().isClosed()){
            return;
        }
        System.out.println("Fechando a conexão");
        BasicSQL.getSQL().close();
        System.out.println("Conexão encerrada.");
    }

}
