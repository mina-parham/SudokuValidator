/**
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MergeSort ;

import java.util.Arrays;

/**
 *
 * @author Zahra Parham - 9412003
 * @author Bahar Mousazadeh - 9412054
 */

public class MergeSort{ 
    
    public static void main(String[] args) throws InterruptedException{
        
        Integer[] array = new Integer[50];
        
        for (int i = 0; i < 50 ; i++){
            array[i] = (int) (Math.random() * ((100 - 1) + 1)) + 1;
		}
		
		System.out.println("Unsorted Values: " + Arrays.toString(array));
		
		Merge<Integer> merge = new Merge<Integer>(array);
                
                merge.start();
                
                merge.join();
		
		merge.getSortedValues(array);
		System.out.println("Sorted Values: " + Arrays.toString(array));
	}

}