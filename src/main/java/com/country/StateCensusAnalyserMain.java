package com.country;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class StateCensusAnalyserMain {
    public static void main (String[] args) {
        System.out.println("Indian States Census Analyser Problem");
    }

    public int loadStateCensusData(String filePathCSV) throws IndianCensusAnalyserException {
        try {
            Reader reader;
            reader = Files.newBufferedReader(Paths.get(filePathCSV));
            CsvToBeanBuilder<StateCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(StateCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<StateCensusCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<StateCensusCSV> stateCensusCSVIterator = csvToBean.iterator();
            Iterable<StateCensusCSV> stateCensusCSVIterable = () -> stateCensusCSVIterator;
            int entries = (int) StreamSupport.stream(stateCensusCSVIterable.spliterator(), false).count();
            return entries;
        } catch (IOException e) {
            throw new IndianCensusAnalyserException(IndianCensusAnalyserException.CensusException.CENSUS_FILE_PROBLEM,"Incorrect File");
        } catch (RuntimeException e) {
            if (ExceptionUtils.indexOfType(e, CsvDataTypeMismatchException.class) != -1) {
                if(e.getMessage().equalsIgnoreCase("CSV Header contains Error")) {
                    throw new IndianCensusAnalyserException(IndianCensusAnalyserException.CensusException.INCORRECT_HEADER_PROBLEM,"Incorrect Header");
                } else {
                    throw new IndianCensusAnalyserException(IndianCensusAnalyserException.CensusException.DELIMITER_ISSUE,"Incorrect Delimiter Issue");
                }
            } else {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }
}
