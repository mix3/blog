package org.mix3.blog;

import org.mix3.blog.service.Module;
import org.mix3.blog.service.Service;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ServiceTest {
	@SuppressWarnings("unused")
	@Inject
	private Service service;
	
	public static void main(String[] args){
		Injector injector = Guice.createInjector(new Module());
		ServiceTest test = injector.getInstance(ServiceTest.class);
		test.test();
	}
	
	private void test(){
//		try {
//			service.migrate();
//			service.insert();
//			service.delete(8);
//			service.show();
//		} catch (SQLException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
	}
}
