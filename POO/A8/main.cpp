#include <iostream>
#include <string>
using namespace std;

// Clase Base
class Persona {
protected:
    string nombre;
    int edad;
    string direccion;

public:
    void registrar(string nombre, int edad, string direccion) {
        this->nombre = nombre;
        this->edad = edad;
        this->direccion = direccion;
    }

    void mostrar() {
        cout << "Nombre: " << nombre << endl;
        cout << "Edad: " << edad << endl;
        cout << "Direccion: " << direccion << endl;
    }
};

// Clase Derivada: Cliente
class Cliente : public Persona {
private:
    int numCliente;
    float saldo;
    string nivel;

public:
    void registrar(int numCliente, float saldo, string nivel, string nombre, int edad, string direccion) {
        this->numCliente = numCliente;
        this->saldo = saldo;
        this->nivel = nivel;
        Persona::registrar(nombre, edad, direccion);
    }

    void mostrar() {
        Persona::mostrar();
        cout << "Numero de Cliente: " << numCliente << endl;
        cout << "Saldo: $" << saldo << endl;
        cout << "Nivel de fidelidad: " << nivel << endl;
    }
};

// Clase Derivada: Empleado
class Empleado : public Persona {
private:
    int numEmpleado;
    string departamento;
    float sueldo;

public:
    void registrar(int numEmpleado, string departamento, float sueldo, string nombre, int edad, string direccion) {
        this->numEmpleado = numEmpleado;
        this->departamento = departamento;
        this->sueldo = sueldo;
        Persona::registrar(nombre, edad, direccion);
    }

    void mostrar() {
        Persona::mostrar();
        cout << "Numero de Empleado: " << numEmpleado << endl;
        cout << "Departamento: " << departamento << endl;
        cout << "Sueldo: $" << sueldo << endl;
    }
};

// Función Principal
int main() {
    Cliente cliente;
    Empleado empleado;

    // Captura datos del cliente
    string nombreCliente, direccionCliente, nivelCliente;
    int edadCliente, numCliente;
    float saldoCliente;

    cout << "Ingrese los datos del cliente:" << endl;
    cout << "Nombre: ";
    getline(cin, nombreCliente);
    cout << "Edad: ";
    cin >> edadCliente;
    cin.ignore(); // Limpiar el buffer
    cout << "Direccion: ";
    getline(cin, direccionCliente);
    cout << "Numero de Cliente: ";
    cin >> numCliente;
    cout << "Saldo: ";
    cin >> saldoCliente;
    cin.ignore(); // Limpiar el buffer
    cout << "Nivel de fidelidad: ";
    getline(cin, nivelCliente);

    cliente.registrar(numCliente, saldoCliente, nivelCliente, nombreCliente, edadCliente, direccionCliente);

    // Captura datos del empleado
    string nombreEmpleado, direccionEmpleado, departamentoEmpleado;
    int edadEmpleado, numEmpleado;
    float sueldoEmpleado;

    cout << "\nIngrese los datos del empleado:" << endl;
    cout << "Nombre: ";
    getline(cin, nombreEmpleado);
    cout << "Edad: ";
    cin >> edadEmpleado;
    cin.ignore(); // Limpiar el buffer
    cout << "Direccion: ";
    getline(cin, direccionEmpleado);
    cout << "Numero de Empleado: ";
    cin >> numEmpleado;
    cin.ignore(); // Limpiar el buffer
    cout << "Departamento: ";
    getline(cin, departamentoEmpleado);
    cout << "Sueldo: ";
    cin >> sueldoEmpleado;

    empleado.registrar(numEmpleado, departamentoEmpleado, sueldoEmpleado, nombreEmpleado, edadEmpleado, direccionEmpleado);

    // Mostrar la información
    cout << "\nInformacion del Cliente:" << endl;
    cliente.mostrar();

    cout << "\nInformacion del Empleado:" << endl;
    empleado.mostrar();

    return 0;
}
