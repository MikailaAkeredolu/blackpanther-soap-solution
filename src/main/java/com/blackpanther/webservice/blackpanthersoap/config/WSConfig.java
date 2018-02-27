package com.blackpanther.webservice.blackpanthersoap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
	//Configure web service beans
	//extend the WSConfigurer Adapter
	//Create a Servlet Registration Bean
	//Create a Default Wsdl Definition Bean
	//Create an xsd Schema Bean
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


@EnableWs
@Configuration
public class WSConfig extends WsConfigurerAdapter {
	
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet,"/ws/*");
	}
	
	@Bean(name="panthers")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema actorSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("ActorsPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://www.blackpanther.com/panther");
		wsdl11Definition.setSchema(actorSchema);
		return wsdl11Definition;
	} 
	//http://<host>:<port>/ws/countries.wsdl - http://localhost:8080/ws/panthers.wsdl
	
	@Bean
	public XsdSchema studentsSchema() {
		return new SimpleXsdSchema(new ClassPathResource("panthers.xsd"));
	}
	
}
/*
 * 1 - 	MessageDispatcherServlet: 
 * 	Servlet for simplified dispatching of Web service messages.
	This servlet is a convenient alternative to the standard Spring-MVC DispatcherServlet
 	with separate WebServiceMessageReceiverHandlerAdapter,
  	MessageDispatcher, and WsdlDefinitionHandlerAdapter instances.
  	
  	 It is important to inject and set ApplicationContext to MessageDispatcherServlet.
  	 Without that, Spring WS will not detect Spring beans automatically.
  	 
  2 - 	DefaultWsdl11Definition exposes a standard WSDL 1.1 using XsdSchema
  http://<host>:<port>/ws/countries.wsdl
  
  3 - 
  	 
*/
