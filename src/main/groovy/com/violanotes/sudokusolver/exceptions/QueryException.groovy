package com.violanotes.sudokusolver.exceptions

import groovy.transform.InheritConstructors;

/**
 * Created by pc on 7/20/2017.
 */
@InheritConstructors
class QueryException extends BoardEntityException {
    QueryException(Class clazz1, Class clazz2) {
        super("error querying for class '${clazz1.simpleName}' in '${clazz2.simpleName}'.  " +
                "Query may not be defined for class '${clazz1.simpleName}'.")
    }
}
