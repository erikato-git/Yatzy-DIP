
class Die {
    /*  
    constructor(){      //spørg Erik
        this(6);
    }
    */
    constructor(sides) {
        this.sides = sides;
        this.faceValues = 1;
    }

    roll(){
        this.faceValues = Math.floor( Math.random() * 6 ) + 1;
    }

    setFaceValue(value){
        if(value > 0 && value <= this.sides){
            this.faceValues = value;
        }
    }

    getFaceValue(){
        return this.faceValues;
    }
}

