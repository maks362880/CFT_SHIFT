package test;


import builder.BuildAndRunSortMethod;
import configReader.ArgsFillOrException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sorters.ClassObjectType;
import sorters.SortingMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArgsFillOrExceptionTest {

//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }

    @Test
    void createUseArgs() {
        String[] args = {"-i","-a","1.txt","2.txt","3.txt","4.txt"};
        int  MaxPartSizeFileKb = 28762;
        BuildAndRunSortMethod brsr = new BuildAndRunSortMethod(args,MaxPartSizeFileKb);
        ArgsFillOrException argsFillOrException = new ArgsFillOrException(args);
        argsFillOrException.createUseArgs(brsr);
        assertEquals(2, brsr.getOffset());
        assertEquals(args, brsr.getArgs());
        assertEquals(ClassObjectType.Integer, brsr.getClassObjectType());
        assertEquals(SortingMethod.Asc, brsr.getSortingMethod());
        assertEquals(28762, brsr.getMaxPartSizeFileKb());
    }
}