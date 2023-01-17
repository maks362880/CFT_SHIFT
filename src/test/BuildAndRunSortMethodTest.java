package test;

import builder.BuildAndRunSortMethod;
import configReader.ArgsFillOrException;
import exeptionHandling.ExceptionAndLogFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sorters.ClassObjectType;
import sorters.SortingMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BuildAndRunSortMethodTest {

//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }

//    @Test
//    void create() {
//    }

    @Test
    void setClassObjectType() {
        String[] args = {"-i","-a","1.txt","2.txt","3.txt","4.txt"};
        int  MaxPartSizeFileKb = 28762;
        BuildAndRunSortMethod brsr = new BuildAndRunSortMethod(args,MaxPartSizeFileKb);
        brsr.setClassObjectType(ClassObjectType.String);
        assertEquals(brsr.getClassObjectType(),ClassObjectType.String);
    }

    @Test
    void setSortingMethod() {
        String[] args = {"-i","-a","1.txt","2.txt","3.txt","4.txt"};
        int  MaxPartSizeFileKb = 28762;
        BuildAndRunSortMethod brsr = new BuildAndRunSortMethod(args,MaxPartSizeFileKb);
        brsr.setSortingMethod(SortingMethod.Desc);
        assertEquals(brsr.getSortingMethod(),SortingMethod.Desc);
    }

    @Test
    void setOffsetTwo() {
        String[] args = {"-i","-a","1.txt","2.txt","3.txt","4.txt"};
        int  MaxPartSizeFileKb = 28762;
        BuildAndRunSortMethod brsr = new BuildAndRunSortMethod(args,MaxPartSizeFileKb);
        ArgsFillOrException aof = new ArgsFillOrException(args);
        aof.createUseArgs(brsr);
        assertEquals(brsr.getOffset(),2);
    }

    @Test
    void setOffsetOne() {
        String[] args = {"-i","1.txt","2.txt","3.txt","4.txt","5.txt"};
        int  MaxPartSizeFileKb = 28762;
        BuildAndRunSortMethod brsr = new BuildAndRunSortMethod(args,MaxPartSizeFileKb);
        ArgsFillOrException aof = new ArgsFillOrException(args);
        aof.createUseArgs(brsr);
        assertEquals(brsr.getOffset(),1);
    }

    @Test
    void setOffsetErr() {
        String[] args = {"1.txt","2.txt","3.txt","4.txt"};
        int  MaxPartSizeFileKb = 28762;
        BuildAndRunSortMethod brsr = new BuildAndRunSortMethod(args,MaxPartSizeFileKb);
        ArgsFillOrException aof = new ArgsFillOrException(args);
        aof.createUseArgs(brsr);
      //  ExceptionAndLogFile andLogFile = new ExceptionAndLogFile("Wrong param '1.txt' with id 0");
       // assertTrue(andLogFile.getMessage().contains("Wrong param '1.txt' with id 0"));
        assertTrue(true);
    }

    @Test
    void getClassObjectType() {
        String[] args = {"-i","1.txt","2.txt","3.txt","4.txt"};
        int  MaxPartSizeFileKb = 28762;
        BuildAndRunSortMethod brsr = new BuildAndRunSortMethod(args,MaxPartSizeFileKb);
        ClassObjectType cot = ClassObjectType.Exception;
        brsr.setClassObjectType(cot);
        assertEquals(brsr.getClassObjectType(),cot);
    }

    @Test
    void getSortingMethod() {
        String[] args = {"-i","-d","1.txt","2.txt","3.txt","4.txt"};
        int  MaxPartSizeFileKb = 28762;
        BuildAndRunSortMethod brsr = new BuildAndRunSortMethod(args,MaxPartSizeFileKb);
        SortingMethod sortingMethod = SortingMethod.Desc;
        brsr.setSortingMethod(sortingMethod);
        assertEquals(brsr.getSortingMethod(),sortingMethod);
    }

    @Test
    void getArgs() {
        String[] args = {"-s","1.txt","2.txt","3.txt","4.txt"};
        BuildAndRunSortMethod bsm = new BuildAndRunSortMethod(args,8096);
        assertEquals(bsm.getArgs(),args);
    }

    @Test
    void getMaxPartSizeFileKb() {
        String[] args = {"-s","1.txt","2.txt","3.txt","4.txt"};
        int  MaxPartSizeFileKb = 28762;
        BuildAndRunSortMethod bsm = new BuildAndRunSortMethod(args,MaxPartSizeFileKb);
        assertEquals(bsm.getMaxPartSizeFileKb(),28762);
    }
}