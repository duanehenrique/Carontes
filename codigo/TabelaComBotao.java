import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TabelaComBotao extends Application {

    private TableView<Produto> tabela;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Configuração da janela
        primaryStage.setTitle("Tabela JavaFX com Botão");

        // Adiciona um ícone à janela
        Image icon = new Image("https://i.imgur.com/phDnO3R.gif");
        primaryStage.getIcons().add(icon);

        // Criação da tabela
        tabela = new TableView<>();

        // Criação das colunas
        TableColumn<Produto, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Produto, Double> colunaPreco = new TableColumn<>("Preço");
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        // Adição das colunas à tabela
        tabela.getColumns().add(colunaNome);
        tabela.getColumns().add(colunaPreco);

        // Adição de dados à tabela (pode ser substituído por uma fonte de dados real)
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("Produto A", 19.99));
        produtos.add(new Produto("Produto B", 29.99));
        produtos.add(new Produto("Produto C", 39.99));

        tabela.getItems().addAll(produtos);

        // Define a cor de fundo da tabela
        tabela.setStyle("-fx-control-inner-background: darkgray;");

        // Criação do botão
        Button botao = new Button("Aumentar Preço");
        botao.setOnAction(e -> aumentarPrecoProdutos());

        // Configuração do layout usando StackPane e VBox
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(tabela, criarVBoxComBotao(botao));

        // Configuração da cena
        Scene cena = new Scene(stackPane, 400, 300);

        // Configuração da cena para a janela principal
        primaryStage.setScene(cena);

        // Tornar a janela visível
        primaryStage.show();
    }

    // Método para criar um VBox com o botão no canto direito inferior
    private VBox criarVBoxComBotao(Button botao) {
        VBox vbox = new VBox(10); // Espaçamento vertical entre os elementos
        vbox.getChildren().add(botao);
        vbox.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT);
        vbox.setPadding(new javafx.geometry.Insets(10)); // Espaçamento externo
        return vbox;
    }

    // Função para aumentar o preço de todos os produtos em 1 real
    private void aumentarPrecoProdutos() {
        for (Produto produto : tabela.getItems()) {
            produto.setPreco(produto.getPreco() + 1.0);
        }

        // Atualizar a exibição dos dados na tabela
        tabela.refresh();
    }

    // Classe de exemplo para representar um produto
    public static class Produto {
        private String nome;
        private double preco;

        public Produto(String nome, double preco) {
            this.nome = nome;
            this.preco = preco;
        }

        public String getNome() {
            return nome;
        }

        public double getPreco() {
            return preco;
        }

        public void setPreco(double preco) {
            this.preco = preco;
        }
    }
}
