

class Yatzy {

    values = [];
    throwCount;
	random = new Random();

    
    constructor(){
    }


    getValues(){
        return values;
    }


    setValues(values){
        this.values = values;
    }


    getThrowCount(){
        return throwCount;
    }


    resetThrowCount(){
        throwCount = 0;
    }


	throwDice(holds) {
		for(let i =0; i < values.length; i++) {
			if(!holds[i]) {
				values[i] = random.nextInt(6) + 1;
			} 
		}
		throwCount++;
	}


	getResults() {
		results = [];
		for (let i = 0; i <= 5; i++) {
			results[i] = this.sameValuePoints(i + 1);
		}
		results[6] = this.onePairPoints();
		results[7] = this.twoPairPoints();
		results[8] = this.threeSamePoints();
		results[9] = this.fourSamePoints();
		results[10] = this.fullHousePoints();
		results[11] = this.smallStraightPoints();
		results[12] = this.largeStraightPoints();
		results[13] = this.chancePoints();
		results[14] = this.yatzyPoints();

		return results;
	}
    

    calcCounts(){
        eyecount = [];
        for(let i = 0; i < values.length; i++){
            eyecount[values[i]]++;
        }
        return eyecount;
    }


    sameValuePoints(value){
        eyecount = [];
        result;
        result = eyecount[values] * value;
        return result;
    }


    onePairPoints(){
        eyecount = calcCounts();
        pairFound = false;
        i = 6;
        sum = 0;

        while(!pairFound && i > 0){
            if(eyecount[i] >= 2){
                sum = i * 2;
                pairFound = true;
            }
            i--;
        }
        return sum;
    }


    twoPairPoints() {
		eyeCount = calcCounts();
		
		pairs = 0;
		i = 6;
		sum = 0;
		
		while(pairs < 2 && i > 0) {
			 if(eyeCount[i] >= 2 ) {
				sum += 2*i;
				pairs++;
			}
			i--;
			
		}
		if(pairs != 2) {
			return 0;
		} else {
			return sum;
		}
		
	}


    threeSamePoints() {
        eyeCount = calcCounts();
            
        threeOfKind = false;
        i = 6;
        sum = 0;
            
        while(!threeOfKind && i > 0) {
             if(eyeCount[i] >= 3 ) {
                sum += 3*i;
                threeOfKind = true;
            }
            i--;
                
        }
        return sum;    
    }
    

    fourSamePoints() {
        eyeCount = calcCounts();
            
        fourOfKind = false;
        i = 6;
        sum = 0;
            
        while(!fourOfKind && i > 0) {
             if(eyeCount[i] >= 4 ) {
                sum += 4*i;
                fourOfKind = true;
            }
            i--;
                
        }
        return sum;
    }


    fullHousePoints() {
		eyeCount = calcCounts();
		twoOfKind = false;
		threeOfKind = false;
		sum = 0;
		
		for(let i = 0; i < eyeCount.length; i++ ) {
			if(eyeCount[i] == 2) {
				twoOfKind = true;
				sum += i*2;
			} if(eyeCount[i] == 3) {
				threeOfKind = true;
				sum += i*3;
			}
			
		}
		if(twoOfKind && threeOfKind) {
			return sum;
		} else {
			return 0;
		}
	}


    smallStraightPoints() {
		eyeCount = calcCounts();
		result = 0;
		i = 1;
		isFound = false;
		
		while(!isFound && i < 6) {
			if(eyeCount[i] == 1) {
				result += i;
			} else {
				isFound = true;
				result = 0;
				
			}
			i++;
		}
		
		return result;
	}


    largeStraightPoints() {
        eyeCount = calcCounts();
        result = 0;
        i = 2;
        isFound = false;
            
        while(!isFound && i < 7) {
            if(eyeCount[i] == 1) {
                result += i;
            } else {
                isFound = true;
                result = 0;
                    
            }
            i++;
        }
            
        return result;
    }


    chancePoints() {
		result = 0;
		
		for(let i = 0; i < values.length; i++) {
			result += values[i];
		}
		if(result > 30) {
			result = 30;
		}
		return result;
	}


    yatzyPoints() {
        eyeCount = calcCounts();
        fourOfKind = false;
        i = 6;
        sum = 0;
            
        while(!fourOfKind && i > 0) {
            if(eyeCount[i] >= 5 ) {
                sum = 50;
                fourOfKind = true;
            }
            i--;
                
        }
        return sum;
    }
    
}



