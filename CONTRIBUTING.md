# Contributing

Thank you for considering contributing to this project!

## Getting Started

1. Fork the repository
2. Clone your fork
3. Create a new branch for your changes

## Development Workflow

### Code Style

- Follow the official [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use 4 spaces for indentation
- Keep lines under 120 characters when possible

### Testing

All changes must include appropriate tests:

```bash
bazel test //...
```

Ensure all tests pass before submitting a pull request.

### Building

```bash
bazel build //...
```

## Commit Guidelines

We follow the [Conventional Commits](https://www.conventionalcommits.org/) specification:

```
<type>: <subject>

<body>

<footer>
```

### Types

- `feat`: A new feature
- `fix`: A bug fix
- `refactor`: Code change that neither fixes a bug nor adds a feature
- `test`: Adding or updating tests
- `docs`: Documentation changes
- `chore`: Changes to build process or tooling

### Examples

```
feat: add support for custom annotations in allopen plugin

This change allows users to specify custom annotations for the
Spring allopen plugin configuration.
```

```
fix: resolve JUnit version mismatch in test dependencies

Updates test imports to use JUnit 4 to match the declared
dependency in MODULE.bazel.
```

## Pull Request Process

1. Update documentation if needed
2. Ensure all tests pass
3. Update the changelog if applicable
4. Submit your pull request with a clear description of changes
5. Wait for review and address any feedback

## Code Review

All submissions require review. We use GitHub pull requests for this purpose.

## Questions?

Feel free to open an issue for any questions or concerns.
