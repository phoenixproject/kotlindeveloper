package codelogic.com.br.kotlinexemplo

open class Carro() {

    var modelo: String? = null
    var chassi: String? = null

    open fun setModelos(modelo: String) {
        this.modelo = modelo
    }

    fun getModelos(): String {
        return this.modelo.toString()
    }

    fun setChassis(chassi: String) {
        this.chassi = chassi
    }

    fun getChassis(): String {
        return chassi.toString()
    }

}