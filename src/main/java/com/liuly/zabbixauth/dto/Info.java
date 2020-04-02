package com.liuly.zabbixauth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.List;

/**
 * Description: 1367636569@qq.com<br/>
 * date: 2018/9/14 10:14<br/>
 *
 * @author Liuly<br />
 * @version 1.0
 * @since JDK 1.8
 */
@JsonFormat(
        shape = JsonFormat.Shape.OBJECT
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Info<E> extends AbstractList<E> implements Serializable {

    private static final long serialVersionUID = 6995912465240536742L;

    private int totalPages;

    private long totalElements;

    private List<E> rows;

    public Info() {
    }

    public Info(List<E> rows, int totalPages, long totalElements) {
        this.rows = rows;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public List<E> getRows() {
        return rows;
    }

    public void setRows(List<E> rows) {
        this.rows = rows;
    }

    public E get(int i) {
        return this.rows.get(i);
    }

    public int size() {
        return this.rows.size();
    }
}