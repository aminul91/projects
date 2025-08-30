#include "mainwindow.h"
#include "./ui_mainwindow.h"
#include "ticketplanwindow.h"
#include <QFile>
#include <QJsonDocument>
#include <QJsonObject>
#include <QJsonArray>
#include <QDebug>


MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    ui->passwordInput->setEchoMode(QLineEdit::Password);
    connect(ui->loginButton, &QPushButton::clicked, this, &MainWindow::onLoginClicked);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::onLoginClicked()
{
    QString username = ui->userNameInput->text();
    QString password = ui->passwordInput->text();
    bool test = false;
    test = isValidLogin(username, password);
    if(test)
    {
        QMessageBox::information(this, "Login", "Login successful!");
        ticketPlanWindow *ticketplanwindow = new ticketPlanWindow();
        ticketplanwindow->show();
        this->close();


    }
    else
    {
        QMessageBox::warning(this, "Login", "Incorrect username or password.");
    }
}

bool MainWindow::isValidLogin(const QString& inputUsername, const QString& inputPassword)
{
    QFile file(QCoreApplication::applicationDirPath() + "/users.json");
    if (!file.open(QIODevice::ReadOnly)) {
        qWarning() << "Failed to open users.json";
        return false;
    }
    QByteArray data = file.readAll();
    file.close();

    QJsonDocument doc = QJsonDocument::fromJson(data);
    if (!doc.isObject()) {
        qWarning() << "Invalid JSON format";
        return false;
    }

    QJsonObject rootObj = doc.object();
    QJsonArray usersArray = rootObj.value("users").toArray();

    for (const QJsonValue& userVal : usersArray) {
        QJsonObject userObj = userVal.toObject();
        QString username = userObj.value("username").toString();
        QString password = userObj.value("password").toString();

        if (username == inputUsername && password == inputPassword) {
            return true;  // Match found
        }
        else
        {
             qWarning() << "Invalid username password";
        }
    }

    return false;  // No match found
}
