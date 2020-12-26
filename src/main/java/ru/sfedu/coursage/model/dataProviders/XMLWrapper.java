package ru.sfedu.coursage.model.dataProviders;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Collection;

@Root(name = "main")
public class XMLWrapper<T> {
    @ElementList(name = "WrapperName", inline = true, required = false)
    public Collection<T> list;

    public XMLWrapper(){

    }
    public XMLWrapper(Collection<T> list){
        this.list = list;
    }

    public Collection<T> getList() {
        return list;
    }
    public void setList(Collection<T> list) {
        this.list = list;
    }
}
