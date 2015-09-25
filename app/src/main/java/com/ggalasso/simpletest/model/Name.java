package com.ggalasso.simpletest.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by Chris on 9/3/2015.
 */

@Root
public class Name {

        //@Path("name[@type='alternate']")
        @Attribute
        private String value;

        @Attribute
        private String sortindex;

        @Attribute
        private String type;

        public Name() {
        }

        public Name(String value) {
                this.value = value;
                this.sortindex = "1";
                this.type = "primary";
        }

        public String getValue() {
                return value;
        }

        public String getType() {
                return type;
        }
}
