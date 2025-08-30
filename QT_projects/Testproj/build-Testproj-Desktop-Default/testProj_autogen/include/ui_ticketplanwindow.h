/********************************************************************************
** Form generated from reading UI file 'ticketplanwindow.ui'
**
** Created by: Qt User Interface Compiler version 5.9.5
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_TICKETPLANWINDOW_H
#define UI_TICKETPLANWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QTableWidget>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_ticketPlanWindow
{
public:
    QLabel *label;
    QPushButton *logOut;
    QTableWidget *ticketPlanTable;

    void setupUi(QWidget *ticketPlanWindow)
    {
        if (ticketPlanWindow->objectName().isEmpty())
            ticketPlanWindow->setObjectName(QStringLiteral("ticketPlanWindow"));
        ticketPlanWindow->resize(400, 300);
        label = new QLabel(ticketPlanWindow);
        label->setObjectName(QStringLiteral("label"));
        label->setGeometry(QRect(60, 10, 261, 20));
        QFont font;
        font.setPointSize(18);
        font.setBold(true);
        label->setFont(font);
        label->setAlignment(Qt::AlignmentFlag::AlignCenter);
        logOut = new QPushButton(ticketPlanWindow);
        logOut->setObjectName(QStringLiteral("logOut"));
        logOut->setGeometry(QRect(160, 240, 75, 24));
        ticketPlanTable = new QTableWidget(ticketPlanWindow);
        if (ticketPlanTable->columnCount() < 3)
            ticketPlanTable->setColumnCount(3);
        QBrush brush(QColor(0, 0, 0, 255));
        brush.setStyle(Qt::SolidPattern);
        QTableWidgetItem *__qtablewidgetitem = new QTableWidgetItem();
        __qtablewidgetitem->setForeground(brush);
        ticketPlanTable->setHorizontalHeaderItem(0, __qtablewidgetitem);
        QTableWidgetItem *__qtablewidgetitem1 = new QTableWidgetItem();
        ticketPlanTable->setHorizontalHeaderItem(1, __qtablewidgetitem1);
        QTableWidgetItem *__qtablewidgetitem2 = new QTableWidgetItem();
        ticketPlanTable->setHorizontalHeaderItem(2, __qtablewidgetitem2);
        if (ticketPlanTable->rowCount() < 3)
            ticketPlanTable->setRowCount(3);
        QTableWidgetItem *__qtablewidgetitem3 = new QTableWidgetItem();
        ticketPlanTable->setVerticalHeaderItem(0, __qtablewidgetitem3);
        QTableWidgetItem *__qtablewidgetitem4 = new QTableWidgetItem();
        ticketPlanTable->setVerticalHeaderItem(1, __qtablewidgetitem4);
        QTableWidgetItem *__qtablewidgetitem5 = new QTableWidgetItem();
        ticketPlanTable->setVerticalHeaderItem(2, __qtablewidgetitem5);
        QTableWidgetItem *__qtablewidgetitem6 = new QTableWidgetItem();
        ticketPlanTable->setItem(0, 0, __qtablewidgetitem6);
        QTableWidgetItem *__qtablewidgetitem7 = new QTableWidgetItem();
        ticketPlanTable->setItem(0, 1, __qtablewidgetitem7);
        QTableWidgetItem *__qtablewidgetitem8 = new QTableWidgetItem();
        ticketPlanTable->setItem(0, 2, __qtablewidgetitem8);
        QTableWidgetItem *__qtablewidgetitem9 = new QTableWidgetItem();
        ticketPlanTable->setItem(1, 0, __qtablewidgetitem9);
        QTableWidgetItem *__qtablewidgetitem10 = new QTableWidgetItem();
        ticketPlanTable->setItem(1, 1, __qtablewidgetitem10);
        QTableWidgetItem *__qtablewidgetitem11 = new QTableWidgetItem();
        ticketPlanTable->setItem(1, 2, __qtablewidgetitem11);
        QTableWidgetItem *__qtablewidgetitem12 = new QTableWidgetItem();
        ticketPlanTable->setItem(2, 0, __qtablewidgetitem12);
        QTableWidgetItem *__qtablewidgetitem13 = new QTableWidgetItem();
        ticketPlanTable->setItem(2, 1, __qtablewidgetitem13);
        QTableWidgetItem *__qtablewidgetitem14 = new QTableWidgetItem();
        ticketPlanTable->setItem(2, 2, __qtablewidgetitem14);
        ticketPlanTable->setObjectName(QStringLiteral("ticketPlanTable"));
        ticketPlanTable->setGeometry(QRect(10, 60, 381, 131));

        retranslateUi(ticketPlanWindow);

        QMetaObject::connectSlotsByName(ticketPlanWindow);
    } // setupUi

    void retranslateUi(QWidget *ticketPlanWindow)
    {
        ticketPlanWindow->setWindowTitle(QApplication::translate("ticketPlanWindow", "Tickets and WorkPlans", Q_NULLPTR));
        label->setText(QApplication::translate("ticketPlanWindow", "Tickets overview", Q_NULLPTR));
        logOut->setText(QApplication::translate("ticketPlanWindow", "Logout", Q_NULLPTR));
        QTableWidgetItem *___qtablewidgetitem = ticketPlanTable->horizontalHeaderItem(0);
        ___qtablewidgetitem->setText(QApplication::translate("ticketPlanWindow", "Task name", Q_NULLPTR));
        QTableWidgetItem *___qtablewidgetitem1 = ticketPlanTable->horizontalHeaderItem(1);
        ___qtablewidgetitem1->setText(QApplication::translate("ticketPlanWindow", "work plan", Q_NULLPTR));
        QTableWidgetItem *___qtablewidgetitem2 = ticketPlanTable->horizontalHeaderItem(2);
        ___qtablewidgetitem2->setText(QApplication::translate("ticketPlanWindow", "status", Q_NULLPTR));

        const bool __sortingEnabled = ticketPlanTable->isSortingEnabled();
        ticketPlanTable->setSortingEnabled(false);
        ticketPlanTable->setSortingEnabled(__sortingEnabled);

    } // retranslateUi

};

namespace Ui {
    class ticketPlanWindow: public Ui_ticketPlanWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_TICKETPLANWINDOW_H
