## 项目名称

AI Detection

## 简介

**AI Detection**
是一个基于Spring框架的后端程序，旨在帮助用户（尤其是学生）降低其文本被AI检测工具识别为AI生成的概率。通过分析和处理用户提交的文本，降AI能够调整和优化文本，使其更加自然和人性化，从而提高通过检测的概率。

## 目录

- [简介](#简介)
- [功能特点](#功能特点)
- [安装](#安装)
- [使用说明](#使用说明)
- [测试代码](#测试代码)

## 功能特点

- **文本分析**：对用户提交的文本进行深度分析，识别出可能导致被AI检测工具识别为AI生成的特征。
- **文本处理**：根据分析结果对文本进行优化和调整，使其更加自然和多样化。
- **文件处理**：支持pdf和doc文件直接上传进行文本处理。
- **实时处理**：快速处理用户提交的文本，并返回优化后的结果。
- **易于集成**：提供RESTful API接口，便于与其他应用和平台集成。

## 安装

### 系统要求

- JDK 17+
- Gradle 8.7+

### 安装步骤

```bash
# 编译项目
./gradlew build
```

## 使用说明

### 运行服务

#### 填写配置
需要在`src/main/resources/application.yml`配置自己的openai API-KEY
``` yml
chatgpt:
  sdk:
    config:
      # 状态；true = 开启、false 关闭
      enabled: true
      api-host: https://api.openai.com/
      api-key: 
```

#### 启动Spring Boot应用 
```bash
# 启动Spring Boot应用
./gradlew bootRun
```

### 发送请求

向服务器发送POST请求以处理文本：

#### 文本降重

```bash
curl --location 'localhost:8091/detections/text' \
--header 'Content-Type: application/json' \
--data '{
    "model": "GPT_3_5_TURBO",
    "content": "文本内容"
}'
```

#### 文件降重

```bash
curl --location 'localhost:8091/detections/file' \
--form 'file=@"文件路径"' \
--form 'model="GPT_3_5_TURBO"'
```

## 测试代码

### chatgpt通信

`src/test/java/com/nemo/detection/integration/ChatgptTest.java`

需要添加自己的apikey

```java
OpenAIConfiguration configuration = new OpenAIConfiguration();
configuration.setApiHost("https://api.openai.com/");
configuration.setApiKey("your api key");
```

