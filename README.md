# QA Designer

QA Designer is a powerful tool for test engineers. It offers a structured grammar for test engineers writing testcases in qd file.

In addition, with ai-model, QA Designer can chat with you as an assistant based on chat-gpt.

Please kindly let us know if you encounter any problem or have any suggestion, via <a href="mailto:jimmyseraph@testops.vip">jimmyseraph@testops.vip</a>

## Basic Usage

Make sure your IDE version is 2022.3 or later. Install the extension and enable it globally.

You can create/edit a file with the ".qd" extension which is the file type for QA Designer.

QA Design Language Syntax:

![sample](https://s1.ax1x.com/2023/06/07/pCijn6f.jpg)
    
## Import / Export

You can import your testcase design from your ms-excel by right-click the target directory in "Project view" and choose "Import from Excel".

Import action will generate several qd file named by "requirement ID".

And also you can export qd files by right-click the target directory or qd file in "Project view" and choose "Export to Excel".

You should set the fields mapping from ms-excel to qd file in "Settings -> Languages and Frameworks -> QA Design Settings -> Export/Import Settings"

## Enable Chat-GPT

You can enable AI chat window by setting OpenAI API-Key in "Settings -> Languages and Frameworks -> QA Design Settings"