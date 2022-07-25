package pl.migibud;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ObjectMapperUtil {
	public static ObjectMapper getObjectMapper() {
		InstanceHolder.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return InstanceHolder.objectMapper;
	}
	private static class InstanceHolder {
		static ObjectMapper objectMapper = new ObjectMapper();
	}
}
