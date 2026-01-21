# Help

## Available Templates

This repository contains multiple Bazel project templates:

- **Kotlin Spring** (`kotlin/spring/`) - Multi-module Spring Boot template
- **Go Gin** (`go/gin/`) - Single-module Gin web framework template

Each template is an independent Bazel workspace with its own `MODULE.bazel`.

## Using a Template

### Option 1: Clone Specific Template (Recommended)

Using Git sparse checkout to get only the template you need:

```bash
# Clone with sparse checkout
git clone --filter=blob:none --sparse https://github.com/EntryDSM/entrydsm-bazel-template
cd entrydsm-bazel-template

# Checkout Kotlin Spring template
git sparse-checkout set kotlin/spring

# OR checkout Go Gin template
git sparse-checkout set go/gin
```

### Option 2: Copy Template Directory

```bash
# Clone entire repository
git clone https://github.com/EntryDSM/entrydsm-bazel-template

# Copy the template you want
cp -r entrydsm-bazel-template/kotlin/spring my-project
# OR
cp -r entrydsm-bazel-template/go/gin my-project

cd my-project
```

---

## Kotlin Spring Template

### Prerequisites

- [Bazelisk](https://github.com/bazelbuild/bazelisk) (recommended) or Bazel 8.5+
- JDK 21 or higher

### Project Structure

```
kotlin/spring/
├── MODULE.bazel        # Bazel module definition
├── BUILD.bazel         # Root build file
├── kotlin.bzl          # Kotlin compiler configuration
├── module-a/           # Example module
└── module-b/           # Example module
```

### Creating a New Module

1. Create the module directory structure:
```bash
mkdir -p module-name/src/main/kotlin
mkdir -p module-name/src/test/kotlin
```

2. Create `module-name/deps.bzl`:
```starlark
MODULE_DEPS = [
    "@maven//:org_springframework_boot_spring_boot_starter_web",
]

TEST_DEPS = [
    "@maven//:junit_junit",
]
```

3. Create `module-name/BUILD.bazel`:
```starlark
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

### Adding Maven Dependencies

1. Add the artifact to `MODULE.bazel`:
```starlark
maven.artifact(
    artifact = "artifact-name",
    group = "group.id",
    version = VERSION_CONSTANT,
)
```

2. Add to your module's `deps.bzl`:
```starlark
MODULE_DEPS = [
    "@maven//:group_id_artifact_name",
]
```

### Building and Testing

```bash
cd kotlin/spring

# Build all targets
bazel build //...

# Run all tests
bazel test //...

# Run a specific module
bazel run //module-name:main
```

---

## Go Gin Template

### Prerequisites

- [Bazelisk](https://github.com/bazelbuild/bazelisk) (recommended) or Bazel 8.5+
- Go 1.25+ (optional, Bazel will download)

### Project Structure

```
go/gin/
├── MODULE.bazel        # Bazel module definition
├── BUILD.bazel         # Gazelle configuration
├── go.mod              # Go module (dependency source of truth)
├── go.sum              # Go checksums
└── cmd/
    └── server/         # Server binary
        ├── main.go
        └── main_test.go
```

### Adding Go Dependencies

1. Add dependency using Go tools:
```bash
cd go/gin
go get github.com/some/package@version
```

2. Update Bazel:
```bash
bazel mod tidy
```

The `go.mod` file is the single source of truth for dependencies.

### Creating New Packages

1. Create your Go files:
```bash
mkdir -p internal/mypackage
# Write your .go files
```

2. Run Gazelle to generate BUILD files:
```bash
bazel run //:gazelle
```

### Building and Testing

```bash
cd go/gin

# Update dependencies
bazel mod tidy

# Build all targets
bazel build //...

# Run all tests
bazel test //...

# Run the server
bazel run //cmd/server
```

### Running the Server Locally

```bash
# Using Bazel
bazel run //cmd/server

# OR using Go directly
go run cmd/server/main.go
```

The server will start on `http://localhost:8080` with:
- `GET /health` - Health check endpoint

---

## Common Commands

### Bazel

```bash
# Clean build cache
bazel clean

# Show build graph
bazel query //...

# Build with verbose output
bazel build //... --verbose_failures

# Run specific test
bazel test //path/to:test_target
```

### Go-Specific (in go/gin/)

```bash
# Update go.mod and go.sum
go mod tidy

# Update Bazel BUILD files
bazel run //:gazelle

# Update dependencies in MODULE.bazel
bazel mod tidy
```

### Kotlin-Specific (in kotlin/spring/)

```bash
# Format Kotlin code
bazel run @kotlin_formatter//file

# Run with JVM debug
bazel run //module:main --jvmopt=-agentlib:jdwp=...
```

---

## Getting Help

- [Bazel Documentation](https://bazel.build/docs)
- [rules_kotlin Documentation](https://github.com/bazelbuild/rules_kotlin)
- [rules_go Documentation](https://github.com/bazel-contrib/rules_go)
- [Gazelle Documentation](https://github.com/bazel-contrib/bazel-gazelle)
- [Issue Tracker](../../issues)

---

## Troubleshooting

### "No such package" errors

Run `bazel clean` and rebuild:
```bash
bazel clean
bazel build //...
```

### Go dependency issues

1. Update go.mod:
```bash
go mod tidy
```

2. Update Bazel:
```bash
bazel mod tidy
```

3. Rebuild:
```bash
bazel build //...
```

### Maven dependency not found (Kotlin)

1. Check dependency is in `MODULE.bazel`
2. Verify the Maven coordinate format:
   - Group: `com.example` → `com_example`
   - Artifact: `my-lib` → `my_lib`
   - Full: `@maven//:com_example_my_lib`
