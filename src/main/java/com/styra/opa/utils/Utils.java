/* 
 * Code generated by Speakeasy (https://speakeasyapi.dev). DO NOT EDIT.
 */

package com.styra.opa.utils;

import java.io.InputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;

import org.openapitools.jackson.nullable.JsonNullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public final class Utils {
    
    private Utils() {
         // prevent instantiation
    }

    // this method exists because primitive comparisons with objects can 
    // give compile errors. By calling this method we force autobox to object
    // version of primitive     
    public static boolean referenceEquals(Object a, Object b) {
        return a == b;
    }
    
    public static String generateURL(String baseURL, String path)
            throws IllegalArgumentException, IllegalAccessException {
        if (baseURL != null && baseURL.endsWith("/")) {
            baseURL = baseURL.substring(0, baseURL.length() - 1);
        }

        return baseURL + path;
    }
    
    public static <T> String generateURL(Class<T> type, String baseURL, String path, JsonNullable<? extends T> params,
            Map<String, Map<String, Map<String, Object>>> globals) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
        if (params.isPresent() && params.get() != null) {
            return generateURL(type, baseURL, path, params.get(), globals);
        } else {
            return baseURL;
        }
    }
    
    public static <T> String generateURL(Class<T> type, String baseURL, String path, Optional<? extends T> params,
            Map<String, Map<String, Map<String, Object>>> globals) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
        if (params.isPresent()) {
            return generateURL(type, baseURL, path, params.get(), globals);
        } else {
            return baseURL;
        }
    }

    public static <T> String generateURL(Class<T> type, String baseURL, String path, T params,
            Map<String, Map<String, Map<String, Object>>> globals)
            throws IllegalArgumentException, IllegalAccessException, JsonProcessingException {
        if (baseURL != null && baseURL.endsWith("/")) {
            baseURL = baseURL.substring(0, baseURL.length() - 1);
        }

        Map<String, String> pathParams = new HashMap<>();

        Field[] fields = type.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            PathParamsMetadata pathParamsMetadata = PathParamsMetadata.parse(field);
            if (pathParamsMetadata == null) {
                continue;
            }

            Object value = params != null ? field.get(params) : null;
            value = resolveOptionals(value);
            value = Utils.populateGlobal(value, field.getName(), "pathParam", globals);
            if (value == null) {
                continue;
            }

            if (pathParamsMetadata.serialization != null && !pathParamsMetadata.serialization.isBlank()) {
                Map<String, String> serialized = parseSerializedParams(pathParamsMetadata, value);
                pathParams.putAll(serialized);
            } else {
                switch (pathParamsMetadata.style) {
                    case "simple":
                        switch (Types.getType(value.getClass())) {
                            case ARRAY:
                                final List<?> array = toList(value);
                                if (array.isEmpty()) {
                                    continue;
                                }

                                pathParams.put(pathParamsMetadata.name,
                                        String.join(",",
                                                array.stream().map(v -> Utils.valToString(v))
                                                        .collect(Collectors.toList())));
                                break;
                            case MAP:
                                Map<?, ?> map = (Map<?, ?>) value;
                                if (map.size() == 0) {
                                    continue;
                                }

                                pathParams.put(pathParamsMetadata.name,
                                        String.join(",", map.entrySet().stream().map(e -> {
                                            if (pathParamsMetadata.explode) {
                                                return String.format("%s=%s", Utils.valToString(e.getKey()),
                                                        Utils.valToString(e.getValue()));
                                            } else {
                                                return String.format("%s,%s", Utils.valToString(e.getKey()),
                                                        Utils.valToString(e.getValue()));
                                            }
                                        }).collect(Collectors.toList())));
                                break;
                            case OBJECT:
                                if (!allowIntrospection(value.getClass())) {
                                    pathParams.put(pathParamsMetadata.name, Utils.valToString(value));
                                    break;
                                }
                                List<String> values = new ArrayList<>();

                                Field[] valueFields = value.getClass().getDeclaredFields();
                                for (Field valueField : valueFields) {
                                    valueField.setAccessible(true);
                                    PathParamsMetadata valuePathParamsMetadata = PathParamsMetadata.parse(valueField);
                                    if (valuePathParamsMetadata == null) {
                                        continue;
                                    }

                                    Object val = valueField.get(value);
                                    val = resolveOptionals(val);
                                    if (val == null) {
                                        continue;
                                    }

                                    if (pathParamsMetadata.explode) {
                                        values.add(String.format("%s=%s", valuePathParamsMetadata.name,
                                                Utils.valToString(val)));
                                    } else {
                                        values.add(String.format("%s,%s", valuePathParamsMetadata.name,
                                                Utils.valToString(val)));
                                    }
                                }

                                pathParams.put(pathParamsMetadata.name, String.join(",", values));
                                break;
                            default:
                                pathParams.put(pathParamsMetadata.name, Utils.valToString(value));
                                break;
                        }
                }
            }
        }

        return baseURL + templateUrl(path, pathParams);
    }

    public static boolean matchContentType(String contentType, String pattern) {
        if (contentType == null || contentType.isBlank()) {
            return false;
        }

        if (contentType.equals(pattern) || pattern.equals("*") || pattern.equals("*/*")) {
            return true;
        }

        String[] contentTypeParts = contentType.split(";");
        if (contentTypeParts.length == 0) {
            return false;
        }
        String mediaType = contentTypeParts[0];

        if (mediaType.equals(pattern)) {
            return true;
        }

        String[] mediaTypeParts = mediaType.split("/");
        if (mediaTypeParts.length == 2) {
            if (String.format("%s/*", mediaTypeParts[0]).equals(pattern)
                    || String.format("*/%s", mediaTypeParts[1]).equals(pattern)) {
                return true;
            }
        }

        return false;
    }
    
    public static boolean allowIntrospection(Class<?> cls) {
        return !cls.equals(BigInteger.class) 
            && !cls.equals(BigDecimal.class)
            && !cls.equals(BigIntegerString.class)
            && !cls.equals(BigDecimalString.class)
            && !cls.equals(LocalDate.class)
            && !cls.equals(OffsetDateTime.class);
    }

    public enum JsonShape {
        STRING, DEFAULT;
    }
 
    public static SerializedBody serializeRequestBody(Object request, String requestField, String serializationMethod, boolean nullable)
            throws NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException, UnsupportedOperationException, IOException {
        return RequestBody.serialize(request, requestField, serializationMethod, nullable);
    }
    
    public static <T extends Object> List<NameValuePair> getQueryParams(Class<T> type, Optional<? extends T> params,
            Map<String, Map<String, Map<String, Object>>> globals) throws Exception {
        if (params.isEmpty()) {
            return Collections.emptyList();
        } else {
            return getQueryParams(type, params.get(), globals);
        }
    }
    
    public static <T extends Object> List<NameValuePair> getQueryParams(Class<T> type, JsonNullable<? extends T> params,
            Map<String, Map<String, Map<String, Object>>> globals) throws Exception {
        if (!params.isPresent() || params.get() == null) {
            return Collections.emptyList();
        } else {
            return getQueryParams(type, params.get(), globals);
        }
    }

    public static <T extends Object> List<NameValuePair> getQueryParams(Class<T> type, T params,
            Map<String, Map<String, Map<String, Object>>> globals) throws Exception {
        return QueryParameters.parseQueryParams(type, params, globals);
    }

    public static HTTPClient configureSecurityClient(HTTPClient client, Object security) throws Exception {
        return Security.createClient(client, security);
    }

    public static String templateUrl(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();

        Pattern p = Pattern.compile("(\\{.*?\\})");
        Matcher m = p.matcher(url);

        while (m.find()) {
            String match = m.group(1);
            String key = match.substring(1, match.length() - 1);
            String value = params.get(key);
            if (value != null) {
                m.appendReplacement(sb, value);
            }
        }
        m.appendTail(sb);

        return sb.toString();
    }

    public static Map<String, List<String>> getHeaders(Object headers) throws Exception {
        if (headers == null) {
            return null;
        }

        Map<String, List<String>> result = new HashMap<>();

        Field[] fields = headers.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            HeaderMetadata headerMetadata = HeaderMetadata.parse(field);
            if (headerMetadata == null) {
                continue;
            }

            Object value = field.get(headers);
            value = resolveOptionals(value);
            if (value == null) {
                continue;
            }

            switch (Types.getType(value.getClass())) {
                case OBJECT: {
                    if (!allowIntrospection(value.getClass())) {
                        break;
                    } 
                    List<String> items = new ArrayList<>();

                    Field[] valueFields = value.getClass().getDeclaredFields();
                    for (Field valueField : valueFields) {
                        valueField.setAccessible(true);
                        HeaderMetadata valueHeaderMetadata = HeaderMetadata.parse(valueField);
                        if (valueHeaderMetadata == null || valueHeaderMetadata.name == null
                                || valueHeaderMetadata.name.isBlank()) {
                            continue;
                        }

                        Object valueFieldValue = valueField.get(value);
                        valueFieldValue = resolveOptionals(valueFieldValue);
                        if (valueFieldValue == null) {
                            continue;
                        }

                        if (headerMetadata.explode) {
                            items.add(
                                    String.format("%s=%s", valueHeaderMetadata.name,
                                            Utils.valToString(valueFieldValue)));
                        } else {
                            items.add(valueHeaderMetadata.name);
                            items.add(Utils.valToString(valueFieldValue));
                        }
                    }

                    if (!result.containsKey(headerMetadata.name)) {
                        result.put(headerMetadata.name, new ArrayList<>());
                    }

                    List<String> values = result.get(headerMetadata.name);
                    values.add(String.join(",", items));

                    break;
                }
                case MAP: {
                    Map<?, ?> map = (Map<?, ?>) value;
                    if (map.size() == 0) {
                        continue;
                    }

                    List<String> items = new ArrayList<>();

                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        if (headerMetadata.explode) {
                            items.add(String.format("%s=%s", Utils.valToString(entry.getKey()),
                                    Utils.valToString(entry.getValue())));
                        } else {
                            items.add(Utils.valToString(entry.getKey()));
                            items.add(Utils.valToString(entry.getValue()));
                        }
                    }

                    if (!result.containsKey(headerMetadata.name)) {
                        result.put(headerMetadata.name, new ArrayList<>());
                    }

                    List<String> values = result.get(headerMetadata.name);
                    values.add(String.join(",", items));

                    break;
                }
                case ARRAY: {
                    final List<?> array = toList(value);

                    if (array.isEmpty()) {
                        continue;
                    }

                    List<String> items = new ArrayList<>();

                    for (Object item : array) {
                        items.add(Utils.valToString(item));
                    }

                    if (!result.containsKey(headerMetadata.name)) {
                        result.put(headerMetadata.name, new ArrayList<>());
                    }

                    List<String> values = result.get(headerMetadata.name);
                    values.add(String.join(",", items));

                    break;
                }
                default: {
                    if (!result.containsKey(headerMetadata.name)) {
                        result.put(headerMetadata.name, new ArrayList<>());
                    }

                    List<String> values = result.get(headerMetadata.name);
                    values.add(Utils.valToString(value));
                    break;
                }
            }
        }

        return result;
    }

    public static String valToString(Object value) {
        switch (Types.getType(value.getClass())) {
            case ENUM:
                try {
                    Field field = value.getClass().getDeclaredField("value");
                    field.setAccessible(true);
                    return String.valueOf(field.get(value));
                } catch (Exception e) {
                    return "ERROR_UNKNOWN_VALUE";
                }
            default:
                return String.valueOf(resolveOptionals(value));
        }
    }

    public static String prefixBearer(String authHeaderValue) {
        if (authHeaderValue.toLowerCase().startsWith("bearer ")) {
            return authHeaderValue;
        }
        return "Bearer " + authHeaderValue;
    }

    public static Object populateGlobal(Object value, String fieldName, String paramType,
            Map<String, Map<String, Map<String, Object>>> globals) {
        if (value == null &&
                globals != null &&
                globals.containsKey("parameters") &&
                globals.get("parameters").containsKey(paramType)) {
            Object globalVal = globals.get("parameters").get(paramType).get(fieldName);
            if (globalVal != null) {
                value = globalVal;
            }
        }

        return value;
    }

    private static Map<String, String> parseSerializedParams(PathParamsMetadata pathParamsMetadata, Object value)
            throws JsonProcessingException {
        Map<String, String> params = new HashMap<>();

        switch (pathParamsMetadata.serialization) {
            case "json":
                ObjectMapper mapper = JSON.getMapper();
                String json = mapper.writeValueAsString(value);

                params.put(pathParamsMetadata.name, URLEncoder.encode(json, StandardCharsets.UTF_8));
                break;
        }

        return params;
    }
    
    public static <T> T checkNotNull(T object, String name) {
        if (object == null) {
            // IAE better than NPE in this use-case (NPE can suggest internal troubles)
            throw new IllegalArgumentException(name + " cannot be null");
        }
        return object;
    }    
    
    public static <K, V> Map<K, V> emptyMapIfNull(Map<K, V> map) {
        return map == null ? java.util.Collections.emptyMap() : map; 
    }
    
    public static String toString(Class<?> cls, Object... items) {
        if (items.length % 2 != 0) {
            throw new IllegalArgumentException("items must have an even length");
        }
        StringBuilder s = new StringBuilder();
        int i = 0;
        while (i < items.length) {
            if (i > 0) {
                s.append(", ");
            }
            s.append(items[i]);
            s.append("=");
            s.append(items[i + 1]);
            i += 2;
        }
        return cls.getSimpleName() + "[" + s + "]";
    }    

    public static Object resolveOptionals(Object o) {
        if (o instanceof Optional) {
            return ((Optional<?>) o).orElse(null);
        } else if (o instanceof JsonNullable) {
            // TODO if JsonNullable.of(null) then we probably want an explicit null
            // to be used by the caller of this so should probably return an EXPLICIT_NULL 
            // (a singleton constant object that represents this scenario without us being
            // coupled to JsonNullable).
            return ((JsonNullable<?>) o).orElse(null);
        } else {
            return o;
        }
    }
    
    public static List<?> toList(Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof List) {
            return (List<?>) o;
        } else if (o.getClass().isArray()) {
            return Arrays.asList((Object[]) o);
        } else {
            throw new IllegalArgumentException("argument must be List or array");
        }
    }
    
    public static <T> T readDefaultOrConstValue(String name, String json, TypeReference<T> typeReference) {
        try {
            return readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("default/const value did not match the expected type, name=" + name + ",json=\n" + json, e); 
        }
    }
    
    private static <T> T readValue(String json, TypeReference<T> typeReference) throws JsonProcessingException {
        return JSON.getMapper().readValue(json, typeReference);
    }
    
    public static byte[] toByteArrayAndClose(InputStream in) throws IOException {
        try {
            return IOUtils.toByteArray(in);
        } finally {
            in.close();
        }
    }
    
    public static String toUtf8AndClose(InputStream in) throws IOException {
        return new String(toByteArrayAndClose(in), StandardCharsets.UTF_8);
    }

    public static Object convertToShape(Object o, JsonShape shape, TypeReference<?> typeReference) {
        if (shape == JsonShape.STRING) {
            return convertToStringShape(o, typeReference);
        } else {
            return o; 
        } 
    }
    
    private static final Map<Class<?>, Function<Object, Object>> STRING_CONVERSIONS = Map.of(//
            BigInteger.class, o -> new BigIntegerString((BigInteger) o), //
            BigDecimal.class, o -> new BigDecimalString((BigDecimal) o));
            
    private static final Map<Class<?>, Function<Object, Object>> STRING_INVERSE_CONVERSIONS = Map.of(//
            BigIntegerString.class, o -> ((BigIntegerString) o).value(), //
            BigDecimalString.class, o -> ((BigDecimalString) o).value());

    private static Object convertToStringShape(Object o, TypeReference<?> typeReference) {
        JavaType jt = JSON
            .getMapper()
            .getTypeFactory()
            .resolveMemberType(typeReference.getType(), null);
        return convertToStringShape(o, jt);
    }
    
    private static Object convertToStringShape(Object o, JavaType jt) {
        if (jt.getRawClass().equals(List.class)) {
            List<?> list = (List<?>) o;
            return list.stream() //
                    .map(x -> convertToStringShape(x, jt.getContentType())) //
                    .collect(Collectors.toList());
        } else if (jt.getRawClass().equals(Map.class)) {
            Map<?, ?> map = (Map<?, ?>) o;
            Map<Object, Object> result = new HashMap<>();
            for (Entry<?, ?> entry : map.entrySet()) {
                result.put(entry.getKey(), convertToStringShape(entry.getValue(), jt.getContentType()));
            }
            return result;
        } else if (jt.getRawClass().equals(Optional.class)) {
            Optional<?> optional = (Optional<?>) o;
            if (optional.isPresent()) {
                return Optional.of(convertToStringShape(optional.get(), jt.getContentType()));
            } else {
                return o;
            }
        } else if (jt.getRawClass().equals(JsonNullable.class)) {
            JsonNullable<?> n = (JsonNullable<?>) o;
            if (n.isPresent()) {
                if (n.get() == null) {
                    return o;
                } else {
                    return JsonNullable.of(convertToStringShape(n.get(), jt.getContentType()));
                }
            } else {
                return o;
            }
        } else if (STRING_CONVERSIONS.containsKey(jt.getRawClass())) {
            return STRING_CONVERSIONS.get(jt.getRawClass()).apply(o);
        } else {
            return o;
        }
    }
    
    private static Object convertToStringShapeInverse(Object o, JavaType jt) {
        if (jt.getRawClass().equals(List.class)) {
            List<?> list = (List<?>) o;
            return list.stream() //
                    .map(x -> convertToStringShapeInverse(x, jt.getContentType())) //
                    .collect(Collectors.toList());
        } else if (jt.getRawClass().equals(Map.class)) {
            Map<?, ?> map = (Map<?, ?>) o;
            Map<Object, Object> result = new HashMap<>();
            for (Entry<?, ?> entry : map.entrySet()) {
                result.put(entry.getKey(), convertToStringShapeInverse(entry.getValue(), jt.getContentType()));
            }
            return result;
        } else if (jt.getRawClass().equals(Optional.class)) {
            Optional<?> optional = (Optional<?>) o;
            if (optional.isPresent()) {
                return Optional.of(convertToStringShapeInverse(optional.get(), jt.getContentType()));
            } else {
                return o;
            }
        } else if (jt.getRawClass().equals(JsonNullable.class)) {
            JsonNullable<?> n = (JsonNullable<?>) o;
            if (n.isPresent()) {
                if (n.get() == null) {
                    return o;
                } else {
                    return JsonNullable.of(convertToStringShapeInverse(n.get(), jt.getContentType()));
                }
            } else {
                return o;
            }
        } else if (STRING_INVERSE_CONVERSIONS.containsKey(jt.getRawClass())) {
            return STRING_INVERSE_CONVERSIONS.get(jt.getRawClass()).apply(o);
        } else {
            return o;
        }
    }
    
    // used for deserialization
    static JavaType convertToShape(TypeFactory f, TypeReference<?> typeReference, JsonShape shape) {
        JavaType jt = f.resolveMemberType(typeReference.getType(), null);
        if (shape == JsonShape.STRING) {
            return convertToStringShape(f, jt);
        } else {
            return jt;
        }
    }
    
    static Object convertToShapeInverse(Object o, JsonShape shape, JavaType jt) {
        if (shape == JsonShape.STRING) {
            TypeFactory f = JSON.getMapper().getTypeFactory();
            return convertToStringShapeInverse(o, jt);
        } else {
            return o;
        }
    }
    
    // VisibleForTesting
    public static JavaType convertToStringShape(TypeFactory f, JavaType a) {
        if (a.getRawClass().equals(List.class)) {
            JavaType b = convertToStringShape(f, a.getContentType());
            return f.constructCollectionType(List.class, b);
        } else if (a.getRawClass().equals(Map.class)) {
            JavaType key = f.constructType(String.class);
            JavaType value = convertToStringShape(f, a.getContentType());
            return f.constructMapType(Map.class, key, value);
        } else if (a.getRawClass().equals(Optional.class)) {
            JavaType b = convertToStringShape(f, a.getContentType());
            return f.constructParametricType(Optional.class, b);
        } else if (a.getRawClass().equals(JsonNullable.class)) {
            JavaType b = convertToStringShape(f, a.getContentType());
            return f.constructParametricType(JsonNullable.class, b);
        } else if (a.getRawClass().equals(BigInteger.class)) {
            return f.constructType(BigIntegerString.class);
        } else if (a.getRawClass().equals(BigDecimal.class)) {
            return f.constructType(BigDecimalString.class);
        } else {
            return a;
        }
    }
    
    public static final class TypeReferenceWithShape {
        private final TypeReference<?> typeReference;
        private final JsonShape shape;
        
        private TypeReferenceWithShape(TypeReference<?> typeReference, JsonShape shape) {
            this.typeReference = typeReference;
            this.shape = shape;
        }
        
        public static TypeReferenceWithShape of(TypeReference<?> typeReference, JsonShape shape) {
            return new TypeReferenceWithShape(typeReference, shape); 
        }
        
        public TypeReference<?> typeReference() {
            return typeReference;
        }
        
        public JsonShape shape() {
            return shape;
        }
    }
    
    static <T> Object resolveStringShape(Class<T> type, String fieldName, Object value) throws IllegalAccessException {
        try {
            // the presence of this TypeReference field indicates that the parameter
            // has a JsonShape of String and that we should convert BigInteger to 
            // BigIntegerString and BigDecimal to BigDecimalString
            // where explicitly mentioned in the TypeReference
            Field tr = type.getDeclaredField(fieldName + "_typeReference");
            tr.setAccessible(true);
            TypeReference<?> typeReference = (TypeReference<?>) tr.get(null);
            // adjust the value so BigInteger and BigDecimal serialize to string
            return convertToShape(value, JsonShape.STRING, typeReference);
        } catch (NoSuchFieldException e) {
            return value;
        }
    }
}