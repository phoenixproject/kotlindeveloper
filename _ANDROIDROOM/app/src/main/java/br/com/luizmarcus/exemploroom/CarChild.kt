package br.com.luizmarcus.exemploroom

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/*@Entity
data class CarChild (
        @NonNull
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        override var name: String
) : CarFather(name)*/

@Entity
data class CarChild (
        @NonNull
        @PrimaryKey(autoGenerate = true)
        var id: Long?
) : CarFather() {
        constructor(id: Nothing?, name: String) : this(id)
}

/*@Entity
data class CarChild (
        @NonNull
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        override var name: String
) : CarFather(name) {
        constructor(id: Nothing?, name: String) : this(id!!, name = name)
}*/

/*@Entity
data class CarChild (
        @NonNull
        @PrimaryKey(autoGenerate = true)
        var id: Long?
) : CarFather(nome = String()) {
        constructor(id: Nothing?, name: String) : super(name){}
}*/

/*@Entity
data class CarChild (
        @NonNull
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        override var name: String = ""
) : CarFather(){
        constructor(name: String,id: Nothing?) : this(id, name)
}*/

/*class CarChild {
        var id: Long?
} : CarFather {
        constructor(name: String,id: Nothing?) : super(name){}
}*/

/*class CarChild: CarFather {
        var id: Long? = null
        constructor(nome: String, id: Long?): super(nome) {
                // code
                this.id = id
        }
        constructor(nome: String, id: Nothing?): super(nome) {
                // code
        }
}*/

/*@Entity
data class CarChild (
        @NonNull
        @PrimaryKey(autoGenerate = true)
        var id: Long?
): CarFather(nome = String()) {
    constructor(nome: String, id: Long?): super(nome) {
        // code
        this.id = id
    }
    constructor(nome: String, id: Nothing?): super(nome) {
        // code
    }
}*/