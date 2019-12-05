package com.config.mvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.util.ModelFactory;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.WrappingTemplateModel;

@SuppressWarnings("deprecation")
public class FreemarkerBeanWraper extends BeansWrapper
{
	protected ModelFactory getModelFactory(Class<?> clazz)
	{
		if (Float.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz))
		{
			return BigDecimalModel.FACTORY;
		}

		if (Function.class.isAssignableFrom(clazz))
		{
			return FunctionModel.FACTORY;
		}
		return super.getModelFactory(clazz);
	}

	private final static class BigDecimalModel extends BeanModel implements TemplateNumberModel
	{
		static final ModelFactory FACTORY = new ModelFactory()
		{
			public TemplateModel create(Object object, ObjectWrapper wrapper)
			{
				return new BigDecimalModel(object, (BeansWrapper) wrapper);
			}
		};

		public BigDecimalModel(Object bg, BeansWrapper wrapper)
		{
			super(bg, wrapper);
		}

		@Override
		public Number getAsNumber() throws TemplateModelException
		{
			return new BigDecimal(String.valueOf(object));
		}
	}

	private final static class FunctionModel<U> extends WrappingTemplateModel implements TemplateMethodModelEx
	{
		private Function<Object, U> fun;

		static final ModelFactory FACTORY = new ModelFactory()
		{
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public TemplateModel create(Object object, ObjectWrapper wrapper)
			{
				return new FunctionModel((Function) object, (BeansWrapper) wrapper);
			}
		};

		public FunctionModel(Function<Object, U> bg, BeansWrapper wrapper)
		{
			super(wrapper);
			this.fun = bg;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Object exec(List args) throws TemplateModelException
		{
			if (args.size() == 1)
			{
				Object key = ((BeansWrapper) getObjectWrapper()).unwrap((TemplateModel) args.get(0));
				Object value = fun.apply(key);
				return wrap(value);
			}
			else
			{
				List unwraps = new ArrayList<>();
				if (args != null)
				{
					for (Object obj : args)
					{
						Object key = ((BeansWrapper) getObjectWrapper()).unwrap((TemplateModel) obj);
						unwraps.add(key);
					}
				}
				Object value = fun.apply(unwraps);
				return wrap(value);
			}

		}
	}
}
