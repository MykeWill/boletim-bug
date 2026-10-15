import spock.lang.Specification

import java.io.ByteArrayOutputStream
import java.io.PrintStream

class BoletimSpec extends Specification {

    def "deve calcular a média somando todas as notas, incluindo a primeira"() {
        given:
        def boletim = new Boletim()
        def notasDaAna = [8.0, 7.5, 9.0]

        when:
        def media = boletim.calcularMedia(notasDaAna)

        then:
        Math.abs(media - 8.17) < 0.01
    }

    def "deve classificar como Recuperação aluno com média entre 5.0 e 6.5"() {
        given:
        def boletim = new Boletim()
        def saida = new ByteArrayOutputStream()
        def saidaOriginal = System.out

        when:
        System.setOut(new PrintStream(saida, true, "UTF-8"))
        boletim.run()

        then:
        saida.toString("UTF-8").contains("Bruno: média 6.00 - Recuperação")

        cleanup:
        System.setOut(saidaOriginal)
    }

    def "deve aprovar aluno com média exatamente 7.0"() {
        given:
        def boletim = new Boletim()

        when:
        def situacao = boletim.situacao(7.0, 7.0, 5.0)

        then:
        situacao == "Aprovado"
    }
}
