<<<<<<< HEAD
package com.africanb.africanb.helper.contrat;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class RequestBase {
    protected String sessionUser;
    protected Integer size;
    protected Integer index;
    protected String lang;
    protected String businessLineCode;
    protected String caseEngine;
    protected Boolean isAnd;
    public static Integer user= null;
    protected Boolean isSimpleLoading;
    protected String[] datasElasticSearchIndices;
}
=======
package com.africanb.africanb.helper.contrat;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class RequestBase {
    protected String sessionUser;
    protected Integer size;
    protected Integer index;
    protected String lang;
    protected String businessLineCode;
    protected String caseEngine;
    protected Boolean isAnd;
    public static Integer user= null;
    protected Boolean isSimpleLoading;
    protected String[] datasElasticSearchIndices;
}
>>>>>>> 2623ac8ab9f0101a6fb81e37d6d14c50fd8b0d2b
