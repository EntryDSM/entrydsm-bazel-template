# Help

## Prerequisites

- [Bazelisk](https://github.com/bazelbuild/bazelisk) (recommended) or Bazel 7.0+
- JDK 17 or higher

## Creating a New Module

1. Create the module directory structure:
```bash
mkdir -p module-name/src/main/kotlin
mkdir -p module-name/src/test/kotlin
```

2. Create `module-name/deps.bzl`:
```bzl
MODULE_DEPS = [
    "@maven//:org_springframework_boot_spring_boot_starter_web",
]

TEST_DEPS = [
    "@maven//:junit_junit",
]
```

3. Create `module-name/BUILD.bazel`:
```bzl
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_binary", "kt_jvm_test")
load("//:kotlin.bzl", "setup_kotlin_compiler", "setup_spring_allopen_plugin")
load("//module-name:deps.bzl", "MODULE_DEPS", "TEST_DEPS")

package(default_visibility = ["//visibility:public"])

setup_kotlin_compiler()
setup_spring_allopen_plugin()

kt_jvm_binary(
    name = "main",
    srcs = glob(["src/main/kotlin/**/*.kt"]),
    main_class = "hs.kr.entrydsm.example.MainKt",
    plugins = ["//:spring_allopen"],
    deps = MODULE_DEPS,
    javac_opts = "//:javac_options",
    kotlinc_opts = "//:kotlinc_options",
)

kt_jvm_test(
    name = "test",
    srcs = glob(["src/test/kotlin/**/*.kt"]),
    test_class = "hs.kr.entrydsm.example.TestClass",
    plugins = ["//:spring_allopen"],
    deps = MODULE_DEPS + TEST_DEPS,
    javac_opts = "//:javac_options",
    kotlinc_opts = "//:kotlinc_options",
)
```

## Adding Dependencies

1. Add the Maven artifact to `MODULE.bazel`:
```bzl
maven.artifact(
    artifact = "artifact-name",
    group = "group.id",
    version = VERSION_CONSTANT,
)
```

2. Add the dependency to your module's `deps.bzl`:
```bzl
MODULE_DEPS = [
    "@maven//:group_id_artifact_name",
]
```

## Building and Testing

```bash
# Build all targets
bazel build //...

# Run all tests
bazel test //...

# Run a specific module
bazel run //module-name:main
```

## Getting Help

- [Bazel Documentation](https://bazel.build/docs)
- [rules_kotlin Documentation](https://github.com/bazelbuild/rules_kotlin)
- [Issue Tracker](../../issues)
