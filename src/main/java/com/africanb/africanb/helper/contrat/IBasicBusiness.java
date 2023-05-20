<<<<<<< HEAD
package com.africanb.africanb.helper.contrat;

import java.text.ParseException;
import java.util.Locale;

public interface IBasicBusiness<T, K> {

    K create(T request, Locale locale) throws ParseException;
    K update(T request, Locale locale) throws ParseException;
    K delete(T request, Locale locale);
    K forceDelete(T request, Locale locale);
    K getAll(Locale locale) throws ParseException;
    K getByCriteria(T request, Locale locale) throws ParseException;

}
=======
package com.africanb.africanb.helper.contrat;

import java.text.ParseException;
import java.util.Locale;

public interface IBasicBusiness<T, K> {

    K create(T request, Locale locale) throws ParseException;
    K update(T request, Locale locale) throws ParseException;
    K delete(T request, Locale locale);
    K forceDelete(T request, Locale locale);
    K getAll(Locale locale) throws ParseException;
    K getByCriteria(T request, Locale locale) throws ParseException;

}
>>>>>>> 2623ac8ab9f0101a6fb81e37d6d14c50fd8b0d2b
