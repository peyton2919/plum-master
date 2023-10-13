package cn.peyton.plum.core.validator.demo;

/**
 * <h3>Hibernate validation验证类</h3>
 *
 * <pre>
 *     如果用hibernate validation 验证;
 *     要导入以下两个包，并把这个类下的注解放开，
 *     并在相应的对象属性上加入hibernate validation 注解,
 *     调用check方法就可以用
 *      <dependency>
             <groupId>javax.validation</groupId>
             <artifactId>validation-api</artifactId>
             <version>1.1.0.Final</version>
         </dependency>

         <dependency>
             <groupId>org.hibernate</groupId>
             <artifactId>hibernate-validator</artifactId>
             <version>5.4.1.Final</version>
         </dependency>
 * </pre>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2018/11/16 16:05
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
public class BeanValidator {
//    /**  全局工厂 */
//    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//
//    /**
//     * <h4>多个类验证[基于注解的验证]</h4>
//     * @param first
//     * @param objects
//     * @return
//     */
//    public static Map<String, String> validateObject(Object first, Object... objects) {
//        if (objects != null && objects.length > 0) {
//            return validateList(Lists.asList(first, objects));
//        }else {
//            return validate(first, new Class[0]);
//        }
//    }
//
//    /**
//     * <h4>单个类验证[基于注解的验证]</h4>
//     * @param param 实体对象
//     * @throws ParamException
//     */
//    public static void check(Object param) throws ParamException {
//        Map<String, String> map = BeanValidator.validateObject(param);
//        if (MapUtils.isNotEmpty(map)) {
//            StringBuffer sb = new StringBuffer();
//            for(String key : map.keySet()){
//                sb.append(map.get(key) + "<br>");
//            }
//            throw new ParamException(sb.toString());
//        }
//    }
//
//    //========================================= Private Method =========================================//
//
//    /**
//     * <h4>单个类的验证</h4>
//     * @param t 校验对象
//     * @param groups
//     * @param <T>
//     * @return
//     */
//    private static <T> Map<String, String> validate(T t, Class... groups) {
//        Validator validator = factory.getValidator();
//
//        Set validateResult = validator.validate(t, groups);
//        if (validateResult.isEmpty()) {//验证为空时返回一个空的
//            return Collections.emptyMap();
//        }else {
//            LinkedHashMap errors = Maps.newLinkedHashMap();
//            Iterator iterator = validateResult.iterator();
//            //封装数据
//            while (iterator.hasNext()) {
//                ConstraintViolation violation = (ConstraintViolation) iterator.next();
//                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
//            }
//            return errors;
//        }
//    }
//
//    /**
//     * <h4>多个类的验证</h4>
//     * @param collection
//     * @return
//     */
//    private static Map<String, String> validateList(Collection<?> collection) {
//        Preconditions.checkNotNull(collection);
//        Iterator iterator = collection.iterator();
//        Map errors;
//        do{
//            if (!iterator.hasNext()) {
//                return Collections.emptyMap();
//            }
//            Object object= iterator.next();
//            errors = validate(object,new Class[0]);
//        }while (errors.isEmpty());
//        return errors;
//    }

}
