package gp.test.comm;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.msgpack.jackson.dataformat.MessagePackFactory;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.gp.common.IdKeys;
//import com.gp.info.InfoId;
//import com.gp.info.InfoIds;
//import com.gp.sync.SyncIdKey;
//import com.gp.sync.dao.info.CenterDistInfo;
//
//public class MsgpackTestJson {
//
//	static ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
//	public static void main(String[] args) throws IOException {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
//		objectMapper.setDateFormat(sdf);
//		InfoIds.withInfoIdModule(objectMapper);
//		test();
//		test1();
//	}
//	
//	public static void test1() throws IOException{
//		
//		CenterDistInfo dist = new CenterDistInfo();
//		InfoId<Long> id = IdKeys.getInfoId(SyncIdKey.CENTER_DIST,12l);
//		dist.setInfoId(id);
//		dist.setTargetEntityCode("TG001");
//		dist.setTargetNodeCode("TGN001");
//		dist.setModifier("M001");
//		dist.setModifyDate(new Date());
//		
//		byte[] bytes = objectMapper.writeValueAsBytes(dist);
//
//		System.out.println(new String(bytes));
//		CenterDistInfo value = objectMapper.readValue(bytes, CenterDistInfo.class);
//
//		System.out.println(value.getInfoId());
//		System.out.println(value.getTargetEntityCode());
//		System.out.println(value.getTargetNodeCode());
//
//		System.out.println(value.getModifier());
//	}
//	public static void test() throws IOException {
//	
//		ExamplePojo orig = new ExamplePojo("komamitsu");
//		orig.setVal(new Date());
//		byte[] bytes = objectMapper.writeValueAsBytes(orig);
//
//		System.out.println(new String(bytes));
//		ExamplePojo value = objectMapper.readValue(bytes, ExamplePojo.class);
//
//		System.out.println(value.getName());
//		System.out.println(value.getVal());
//	}
//
//	public static class ExamplePojo {
//		
//		public ExamplePojo() {}
//		
//		public ExamplePojo(String name) {
//			this.name = name;
//		}
//
//		private String name;
//
//		private Date val;
//
//		public Date getVal() {
//			return val;
//		}
//
//		public void setVal(Date val) {
//			this.val = val;
//		}
//
//		public String getName() {
//			return name;
//		}
//
//		public void setName(String name) {
//			this.name = name;
//		}
//
//	}
//}
