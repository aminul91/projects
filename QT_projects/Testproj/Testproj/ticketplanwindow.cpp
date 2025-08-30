#include "ticketplanwindow.h"
#include "ui_ticketplanwindow.h"
#include "mainwindow.h"
#include <QFile>
#include <QJsonDocument>
#include <QJsonArray>
#include <QJsonObject>
#include <QTableWidgetItem>


ticketPlanWindow::ticketPlanWindow(QWidget *parent)
    : QWidget(parent)
    , ui(new Ui::ticketPlanWindow)
{
    ui->setupUi(this);
    loadticketPlans();

    // Row selection instead of cell selection
    ui->ticketPlanTable->setSelectionBehavior(QAbstractItemView::SelectRows);
    ui->ticketPlanTable->setSelectionMode(QAbstractItemView::SingleSelection);
    ui->ticketPlanTable->setHorizontalHeaderLabels({"Name", "Test Cases", "Status"});
    connect(ui->logOut, &QPushButton::clicked, this, &ticketPlanWindow::onlogOutClicked);
}
void ticketPlanWindow::onlogOutClicked()
{

    QMessageBox::StandardButton reply;
    reply = QMessageBox::question(this,"Logout","Do you want to log out",QMessageBox::Yes | QMessageBox::No);
    if(reply == QMessageBox::Yes)
    {
    MainWindow *returnMainWindow = new MainWindow();
    returnMainWindow->show();
    this->close();
    }

}
void ticketPlanWindow::loadticketPlans()
{
    QString filePath = QCoreApplication::applicationDirPath() + "/testplans.json";
    QFile file(filePath);

    if (!file.open(QIODevice::ReadOnly)) {
        QMessageBox::warning(this, "Error", "Failed to open ticketplans.json at: " + filePath);
        return;
    }

    QByteArray data = file.readAll();
    file.close();

    QJsonDocument doc = QJsonDocument::fromJson(data);
    if (!doc.isObject()) {
        QMessageBox::warning(this, "Error", "Invalid JSON format");
        return;
    }

    QJsonObject rootObj = doc.object();
    QJsonArray array = rootObj.value("users").toArray();  // access the "users" array
    ui->ticketPlanTable->setRowCount(array.size());
    ui->ticketPlanTable->setColumnCount(3);
    ui->ticketPlanTable->setHorizontalHeaderLabels({"Task Name", "Task Numbers", "Status"});

    for (int i = 0; i < array.size(); ++i) {
        QJsonObject obj = array[i].toObject();
        QTableWidgetItem *nameItem = new QTableWidgetItem(obj["name"].toString());
        QTableWidgetItem *casesItem = new QTableWidgetItem(QString::number(obj["test_cases"].toInt()));
        QTableWidgetItem *statusItem = new QTableWidgetItem(obj["status"].toString());

        ui->ticketPlanTable->setItem(i, 0, nameItem);
        ui->ticketPlanTable->setItem(i, 1, casesItem);
        ui->ticketPlanTable->setItem(i, 2, statusItem);
    }
}

ticketPlanWindow::~ticketPlanWindow()
{
    delete ui;
}
