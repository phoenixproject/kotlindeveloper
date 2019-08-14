package codelogic.com.br.kotlinexemplo

class Fiat : Carro() {

    override
    fun setModelos(modelo: String) {
        this.modelo = "Bla bla bla"
    }

}