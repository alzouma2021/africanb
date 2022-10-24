package com.africanb.africanb.helper.contrat;



public class RequestBase {
    protected Integer size;
    protected Integer index;

    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }
    public Integer getIndex() {
        return index;
    }
    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "RequestBase{" +
                "size=" + size +
                ", index=" + index +
                '}';
    }
}
