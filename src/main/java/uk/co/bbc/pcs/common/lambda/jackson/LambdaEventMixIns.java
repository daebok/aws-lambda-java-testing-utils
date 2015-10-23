package uk.co.bbc.pcs.common.lambda.jackson;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/**
 * Generic Lambda event mix ins
 */
public class LambdaEventMixIns {

    public static abstract class LambdaEventMixIn {
        @JsonIgnore public static final String RECORDS = "Records";

        @JsonProperty(RECORDS) public abstract String getRecords();
        @JsonProperty(RECORDS) public abstract String setRecords();
    }

    public static abstract class AttributeValueMixIn {
        @JsonIgnore public static final String S = "S";
        @JsonIgnore public static final String N = "N";
        @JsonIgnore public static final String B = "B";
        @JsonIgnore public static final String NULL = "NULL";
        @JsonIgnore public static final String BOOL = "BOOL";
        @JsonIgnore public static final String SS = "SS";
        @JsonIgnore public static final String NS = "NS";
        @JsonIgnore public static final String BS = "BS";
        @JsonIgnore public static final String M = "M";
        @JsonIgnore public static final String L = "L";

        @JsonProperty(S) public abstract String getS();
        @JsonProperty(S) public abstract void setS(String s);
        @JsonProperty(N) public abstract String getN();
        @JsonProperty(N) public abstract void setN(String n);
        @JsonProperty(B) public abstract ByteBuffer getB();
        @JsonProperty(B) public abstract void setB(ByteBuffer b);
        @JsonProperty(NULL) public abstract Boolean isNULL();
        @JsonProperty(NULL) public abstract void setNULL(Boolean nU);
        @JsonProperty(BOOL) public abstract Boolean getBOOL();
        @JsonProperty(BOOL) public abstract void setBOOL(Boolean bO);
        @JsonProperty(SS) public abstract List<String> getSS();
        @JsonProperty(SS) public abstract void setSS(List<String> sS);
        @JsonProperty(NS) public abstract List<String> getNS();
        @JsonProperty(NS) public abstract void setNS(List<String> nS);
        @JsonProperty(BS) public abstract List<String> getBS();
        @JsonProperty(BS) public abstract void setBS(List<String> bS);
        @JsonProperty(M) public abstract Map<String, AttributeValue> getM();
        @JsonProperty(M) public abstract void setM(Map<String, AttributeValue> val);
        @JsonProperty(L) public abstract List<AttributeValue> getL();
        @JsonProperty(L) public abstract void setL(List<AttributeValue> val);
    }

}
