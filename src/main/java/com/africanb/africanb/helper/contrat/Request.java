package com.africanb.africanb.helper.contrat;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL) //permet d'ignoré les valeurs nulls
public class Request<T> extends RequestBase{
    private T data;
    private List<T> datas;

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public List<T> getDatas() {
        return datas;
    }
    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "Request{" +
                "data=" + data +
                ", datas=" + datas +
                ", size=" + size +
                ", index=" + index +
                '}';
    }
}
