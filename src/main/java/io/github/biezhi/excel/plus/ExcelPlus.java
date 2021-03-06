package io.github.biezhi.excel.plus;

import io.github.biezhi.excel.plus.exception.ExcelException;
import io.github.biezhi.excel.plus.reader.ExcelReader;
import io.github.biezhi.excel.plus.style.ExcelStyle;
import io.github.biezhi.excel.plus.writer.Exporter;
import io.github.biezhi.excel.plus.writer.FileExcelWriter;
import io.github.biezhi.excel.plus.writer.ResponseExcelWriter;
import io.github.biezhi.excel.plus.writer.ResponseWrapper;
import jxl.Workbook;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * Excel Plus
 * <p>
 * Provides methods to read and write Excel documents without any implementation.
 *
 * @author biezhi
 * @date 2018/2/4
 */
public class ExcelPlus {

    /**
     * Export Excel configuration parameters
     */
    private Exporter exporter;

    /**
     * Sets the data for exporting a collection container.
     *
     * @param data collection data
     * @param <T>  Java Type
     * @return self, aspect follow-up
     */
    public <T> ExcelPlus export(Collection<T> data) {
        return this.export(data, null);
    }

    /**
     * Sets the data for exporting a collection container.
     *
     * @param data  collection data
     * @param style set the export Excel style, including colors, fonts, and so on
     * @param <T>   Java Type
     * @return self, aspect follow-up
     */
    public <T> ExcelPlus export(Collection<T> data, ExcelStyle style) {
        this.exporter = Exporter.create(data, style);
        return this;
    }

    /**
     * Custom a Exporter object is used to export Excel.
     *
     * @param exporter exporter
     * @param <T>      Java Type
     * @return self, aspect follow-up
     */
    public <T> ExcelPlus export(Exporter<T> exporter) {
        this.exporter = exporter;
        return this;
    }

    /**
     * Writes the exported data to the file.
     *
     * @param file file object
     * @throws ExcelException
     */
    public void writeAsFile(File file) throws ExcelException {
        new FileExcelWriter(file).export(exporter);
    }

    /**
     * Writes the exported data to the ResponseWrapper.
     * <p>
     * The Wrapper pattern is used here, and not all people use ServletResponse.
     *
     * @param wrapper A Servlet Response package requires a filename to be set,
     *                and you can use the ResponseWrapper.create method or its default constructor.
     * @throws ExcelException
     */
    public void writeAsResponse(ResponseWrapper wrapper) throws ExcelException {
        new ResponseExcelWriter(wrapper).export(exporter);
    }

    /**
     * Read an Excel file as a List container.
     *
     * @param file excel file
     * @param type save to Java Type
     * @param <T>  Java Type
     * @return read the Excel rows
     * @throws ExcelException
     */
    public <T> List<T> readAsFile(File file, Class<T> type) throws ExcelException {
        try {
            return new ExcelReader<>(Workbook.getWorkbook(file), type).read();
        } catch (Exception e) {
            throw new ExcelException(e);
        }
    }

    /**
     * Read an Excel inputStream as a List container.
     *
     * @param inputStream excel inputStream object
     * @param type        save to Java Type
     * @param <T>         Java Type
     * @return read the Excel rows
     * @throws ExcelException
     */
    public <T> List<T> readAsStream(InputStream inputStream, Class<T> type) throws ExcelException {
        try {
            return new ExcelReader<>(Workbook.getWorkbook(inputStream), type).read();
        } catch (Exception e) {
            throw new ExcelException(e);
        }
    }
}
