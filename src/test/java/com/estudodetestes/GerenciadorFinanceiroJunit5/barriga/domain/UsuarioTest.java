package com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain;

import com.estudodetestes.GerenciadorFinanceiroJunit5.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.UsuarioBuilder.umUsuario;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Nome da classe de teste é livre porém é convenção escrever o nome da classe testada + sufixo Test
 */
class UsuarioTest {

    /**
     * Nomes dos métodos devem declarar exatamente o que o metodo de teste irá reatornar/realizar
     * @DisplayName pode ser usado no nome da classe ou no nome do método para usar acentos ou colocar nomes com mais clareza ainda do que o nome do método
     */
    @Test
    @DisplayName("Deve criar um usuário válido")
    public void deveCriarUsuarioValido(){
        Usuario usuario = new Usuario(1L, "Usuario valido", "user@email.com", "123456");
        assertNotNull(usuario);
        assertEquals(1L, usuario.getId());
        assertEquals("Usuario valido", usuario.getNome());
        assertEquals("user@email.com", usuario.getEmail());
        assertEquals("123456", usuario.getSenha());

        /**
         * Estratégia do Junit5 para agrupar as assertivas e garantir que ao uma assertiva falhar, as demais ainda serão verificadas
         * No stackTrace do erro ele trará todos os problemas da assertiva.
         * Ponto negativo é a diferença da rastreabilidade pois ao clicar no erro mostrado ele não direciona exatamente para a assertiva que falhou e sim para o AssertAll,
         * dependendo da analise do stackTrace para identificar o erro e linha do erro.
         * Também é possivel identificar na stackTrace o numero da lambda que falhou, começando em 0. "lambda$deveCriarUsuarioValido$1"
         */

        Assertions.assertAll(
                () -> assertNotNull(usuario),
                () -> assertEquals(1L, usuario.getId()),
                () -> assertEquals("user@email.com", usuario.getEmail()),
                () -> assertEquals("123456", usuario.getSenha())
        );
    }

    /**
     * As expressões lambdas também são utilizadas para tratar exceções
     */
    @Test
    @DisplayName("Deve rejeitar a criação de um usuário sem nome")
    public void deveRejeitarUsuarioSemNome(){
        ValidationException ex = assertThrows(ValidationException.class, () ->
                new Usuario(1L, null, "user@email.com", "123456")
        );
        assertEquals("Nome é obrigatorio", ex.getMessage());
    }

    @Test
    @DisplayName("Deve criar um usuário válido usando o UsuarioBuilder")
    public void deveCriarUsuarioValidoComUsuarioBuilder(){
        Usuario usuario = umUsuario().agora();

        Assertions.assertAll(
                () -> assertNotNull(usuario),
                () -> assertEquals(1L, usuario.getId()),
                () -> assertEquals("user@email.com", usuario.getEmail()),
                () -> assertEquals("123456", usuario.getSenha())
        );
    }

    @Test
    @DisplayName("Deve rejeitar a criação de um usuário sem nome usando o UsuarioBuilder")
    public void deveRejeitarUsuarioSemNomeComUsuarioBuilder(){
        ValidationException ex = assertThrows(ValidationException.class, () ->
                umUsuario().comNome(null).agora()
        );
        assertEquals("Nome é obrigatorio", ex.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar a criação de um usuário sem email usando o UsuarioBuilder")
    public void deveRejeitarUsuarioSemEmailComUsuarioBuilder(){
        ValidationException ex = assertThrows(ValidationException.class, () ->
                umUsuario().comEmail(null).agora()
        );
        assertEquals("Email é obrigatorio", ex.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar a criação de um usuário sem senha usando o UsuarioBuilder")
    public void deveRejeitarUsuarioSemSenhaComUsuarioBuilder(){
        ValidationException ex = assertThrows(ValidationException.class, () ->
                umUsuario().comSenha(null).agora()
        );
        assertEquals("Senha é obrigatorio", ex.getMessage());
    }

    @ParameterizedTest(name = "[{index}] - {4}")
    @CsvSource(value = {
            "1, NULL, email@email.com, 1234, Nome é obrigatorio",
            "1, nome, NULL, 1234, Email é obrigatorio",
            "1, nome, email@email.com, NULL, Senha é obrigatorio"
    }, nullValues = "NULL")
    public void deveRejeitarUsuariosComValoresObrigatoriosNulosModelo2(Long id, String nome, String email, String senha, String message){
        ValidationException ex = assertThrows(ValidationException.class, () ->
                umUsuario().comId(id).comNome(nome).comEmail(email).comSenha(senha).agora()
        );
        assertEquals(message, ex.getMessage());
    }

    @ParameterizedTest(name = "[{index}] - {4}")
    @CsvFileSource(files ={"src/test/resources/validacaoCamposObrigatoriosParaUsuario"}, nullValues = "NULL", numLinesToSkip = 1)
    public void deveRejeitarUsuariosComValoresObrigatoriosNulosModelo3(Long id, String nome, String email, String senha, String message){
        ValidationException ex = assertThrows(ValidationException.class, () ->
                umUsuario().comId(id).comNome(nome).comEmail(email).comSenha(senha).agora()
        );
        assertEquals(message, ex.getMessage());
    }

    /**
    *  useHeadersInDisplayName = true
    *  considera que o arquivo csv tem um header e que deve pular essa linha
    */
    @ParameterizedTest
    @CsvFileSource(files ={"src/test/resources/validacaoCamposObrigatoriosParaUsuario"}, nullValues = "NULL", useHeadersInDisplayName = true)
    public void deveRejeitarUsuariosComValoresObrigatoriosNulosModelo4(Long id, String nome, String email, String senha, String message){
        ValidationException ex = assertThrows(ValidationException.class, () ->
                umUsuario().comId(id).comNome(nome).comEmail(email).comSenha(senha).agora()
        );
        assertEquals(message, ex.getMessage());
    }


//    //Modelo levantado para duvida no curso
//    @ParameterizedTest
//    @MethodSource(value = "proveValoresParaValidacaoDeCamposObrigatorios")
//    public void deveRejeitarUsuariosComValoresObrigatoriosNulos(Long id, String nome, String email, String senha, String message) {
//        ValidationException ex = assertThrows(ValidationException.class, () ->
//                umUsuario().comId(id).comNome(nome).comEmail(email).comSenha(senha).agora()
//        );
//        assertEquals(message, ex.getMessage());
//    }
//
//    static Stream<Arguments> proveValoresParaValidacaoDeCamposObrigatorios() {
//        return Stream.of(
//                Arguments.of(1L, null, "email@email.com", "1234", "Nome é obrigatório"),
//                Arguments.of(1L, "Nome", null, "1234", "Email é obrigatório"),
//                Arguments.of(1L, "Nome", "email@email.com", null, "Senha é obrigatório")
//        );
//    }

}