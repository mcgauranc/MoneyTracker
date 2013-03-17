package com.wraith.json;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 21:11
 */
public class ISO8601DateFormatModule /*extends SimpleModule*/ {

//    public ISO8601DateFormatModule() {
//        super("ISO-8601", Version.unknownVersion());
//    }
//
//    public void setupModule(Module.SetupContext context) {
//        super.setupModule(context);
//
//        context.getDeserializationConfig().withDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
//
//        SimpleSerializers simpleSerializers = new SimpleSerializers();
//        simpleSerializers.addSerializer(Timestamp.class, new SerializerBase<Timestamp>(Timestamp.class) {
//            @Override
//            public void serialize(Timestamp value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
//                jgen.writeString(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(value));
//            }
//        });
//        context.addSerializers(simpleSerializers);
//    }
}
