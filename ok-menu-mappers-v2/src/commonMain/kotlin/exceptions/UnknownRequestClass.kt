package exceptions

class UnknownRequestClass(clazz: Any) : RuntimeException("Class $clazz cannot be mapped to MkplContext")
