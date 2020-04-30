# Interfaz Gráfica en Java

Curso propuesto por el grupo de trabajo Semana de Ingenio y Diseño (**SID**) de la Universidad Distrital Francisco Jose de Caldas.

## Monitor

**Cristian Felipe Patiño Cáceres** - Estudiante de Ingeniería de Sistemas de la Universidad Distrital Francisco Jose de Caldas

# Clase 7

## Objetivos

* Comprender el concepto de reutilización de componentes gráficos y sus ventajas a la hora de desarrollar interfaces gráficas de usuario.
* Identificar cuando es necesario realizar la reutilización de componentes y que enfoque es mejor realizar para esto.

# Antes de Comenzar

* Vamos a crear un nuevo color en el servicio **RecursosService**, recordamos el proceso de creación de un objeto gráfico dentro de este servicio:

**Declaración:**
```javascript
private Color colorGrisClaro;
```

**Ejemplificacón:**
```javascript
colorGrisClaro = new Color(249, 249, 249);
```

**Método get:**
```javascript
public Color getColorGrisClaro(){
    return colorGrisClaro;
}
```

* También vamos a crear un borde gris para limitar nuestros objetos gráficos:

**Declaración:**
```javascript
private Border borderGris;
```

**Ejemplificación:**
```javascript
borderGris = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true);
```

**Método get:**
```javascript
public Border getBorderGris(){
    return borderGris;
}
```

***Nota:** Recordemos que estos objetos decoradores dentro del servicio se crean pensando en que serán utilizados en varias partes del proyecto, si ese no es el caso entonces el objeto decorador se debe crear en el componente gráfico correspondiente donde se unicamente se necesita.

Recordando un poco nuestro recorrido tenemos nuestra vista principal con la integración de varios componentes, tenemos ademas un control de visibilidad mediante el enrutamiento de componentes gráficos y revisamos la importancia de controlar la creación de los componentes gráficos.

<div align='center'>
    <img  src='./resources/interfaz1.png'>
    <p>Vista Principal y Single-Page App</p>
</div>

# Reutilización de componentes gráficos

En la clase de hoy nos enfocaremos en un único tema, este sera el de la reutilización de componentes y como cuando realizar esta practica:

* Reutilizacion de componentes gráficos.
    * Enfoque de reutilización por incorporación.
    * Enfoque de reutilización por posicionamiento.

# Reutilización de componentes gráficos

En la clase pasada creamos una serie de componentes que aun no tienen nada adentro mas que un color de fondo, entre estos componentes están el componente de inicio, perfil, amigos, productos y configuraciones. En la clase de hoy vamos a construir el componente Inicio utilizando la reutilizacion de otros componentes que crearemos para esto.

## Concepto de reutilización

Para entender un poco lo que queremos hacer vamos a ver cual es la expectativa a la que queremos llegar creando nuestro componente **inicio**, a continuación veremos el resultado al que se quiere llegar:  

<div align='center'>
    <img  src='./resources/interfaz2.png'>
    <p>Resultado de construcción de componente Inicio</p>
</div>

Si nos damos cuenta nuestro componente gráfico **inicio** tiene varios objetos gráficos que lo incorporan, tiene bastantes imágenes y texto que los conforma. Sin embargo si vemos a detalle podemos darnos cuenta de que existen ciertas particularidades con los objetos gráficos que componen a **inicio**.

Primero vamos a fijarnos en la parte superior, se puede ver que hay tres paneles y los tres están conformados por los mismos objetos gráficos, los tres tienen una imagen en la parte superior, un titulo y un párrafo.

<div align='center'>
    <img  src='./resources/interfaz3.png'>
    <p>Paneles de la parte superior con un mismo patron en sus objetos gráficos</p>
</div>

Fijémonos ahora de la parte inferior se puede notar hay un panel que contiene un conjunto de paneles que están distribuidos uniformemente a lo largo del panel contenedor. Noten que cada uno de estos paneles cuentan con un patron, todos tienen un borde que los separa unos de otros, un icono un titulo y un párrafo.

<div align='center'>
    <img  src='./resources/interfaz4.png'>
    <p>Paneles de la parte inferior con un mismo patron en sus objetos gráficos</p>
</div>

El componente **inicio** podría escribirse completamente en la clase **Template** sin depender de ningún otro componente para su construcción y no esta mal, es una forma de hacerlo y esta bien. Sin embargo imaginen todo el código repetido que va existir dentro de esta clase para mostrar 3 paneles que tienen una misma estructura y otros 6 paneles que tienen esta misma condición.  Realmente es muy tedioso y llenara la clase **Template** de mucho código repetido que afectara en la forma en que se puede analizar mantener y entender el código.

Una alternativa a esto es la construcción de componentes gráficos que encapsulen el código de este patron identificado y podamos hacer uso de el. A continuación vamos a ver como hacer esto posible. 

***Nota:** Vamos a ver 2 enfoques para hacer reutilización de componentes gráficos, una basada en la incorporación y otra basada en el posicionamiento.*

## Construcción base del componente inicio

Vamos a construir la base del componente **inicio** en su clase **Template** vamos a tener los paneles y ciertos objetos más:

* Primero vamos a obtener los servicios **ObjGraficosService** y **RecursosService** como lo hemos hecho varias veces en anteriores clases:

**Declaración:**
```javascript
private ObjGraficosService sObjGraficos;
private RecursosService sRecursos; 
```

**Obtención de servicios:**
```javascript
// Dentro del Constructor
sObjGraficos = ObjGraficosService.getService();
sRecursos = RecursosService.getService();
```

* Vamos a proporcionar de otro color de fondo a nuestro componente:

```javascript
this.setBackground(sRecursos.getColorGrisClaro());
```

* Vamos a crear paneles base para contener los objetos gráficos que queremos visibles en la ventana principal:

**Declaración:**
```javascript
// Declaración Objetos Gráficos 
private JPanel pMision, pVision, pNosotros, pAcciones;
private JLabel lAcciones;
```
**Método crearJPanels:**
```javascript
public void crearJPanels(){
    this.pMision = sObjGraficos.construirJPanel(20, 15, 256, 230, Color.WHITE, null);
    this.add(pMision);

    this.pVision = sObjGraficos.construirJPanel(296, 15, 256, 230, Color.WHITE, null);
    this.add(pVision);
    
    this.pNosotros = sObjGraficos.construirJPanel(572, 15, 256, 230, Color.WHITE, null);
    this.add(pNosotros);

    this.pAcciones = sObjGraficos.construirJPanel(20, 260, 810, 330, Color.WHITE, null);
    this.add(pAcciones);
}
```

**llamada del método dentro del constructor:**
```javascript
// Dentro del constructor
this.crearJPanels();
```

Si corremos nuestra aplicación y oprimimos el botón que llama al componente **inicio** se ve asi:

<div align='center'>
    <img  src='./resources/interfaz5.png'>
    <p>Componente inicio con sus paneles base adicionados</p>
</div>

En este componente vamos a realizar la modularización un tanto distinta, esta vez no vamos a separar la creación por tipo de objetos gráficos, vamos a separar la creación por paneles, con una pequeña excepción en la creación de objetos decoradores, por ahora solo vamos a crear los métodos y los dejaremos vacíos:

```javascript
public void crearObjetosDecoradores(){
}
```
```javascript
public void crearContenidoPMision(){
}
```
```javascript
public void crearContenidoPVision(){
}
```
```javascript
public void crearContenidoPNosotros(){
}
```
```javascript
public void crearContenidoPAcciones(){
}
```

# Enfoque de reutilización por incorporación.

Cuando un componente incorpora otros componentes para su construcción creamos una relación de **Componente Padre** Y **Componente Hijo**. Muchas veces vamos a encontrar este tipo de relaciónes, un ejemplo que ya hemos realizado es entre el componente **VistaPrincipal** y el componente **inicio**. Ahora el mismo componente **inicio** tendrá otros componentes para su construcción y estos serán los hijos de **inicio**. 

Este enfoque se caracteriza por que cuando creemos el **componente hijo**, este va a reemplazar un panel que tengamos previamente creado en el **componente padre**, por ejemplo ya tenemos los paneles **pMision, pVision, pNosotros** en el componente padre. Entonces no nos tenemos que preocupar con la locación del componente hijo, esta ya se configuro cuando se crearon los paneles de reemplazo, lo que si debemos tener pendiente es que tanto el panel como el componente hijo tengan el mismo tamaño y de esa forma no hayan problemas de posicionamiento. Este enfoque ya lo hemos realizado antes en la clase anterior.

Vamos a crear un nuevo component que encapsule la creación del contenido de los paneles superiores, este componente sera llamado **tarjeta**, para esto creamos su respectivo paquete y clases dentro del directorio **components**:

<div align='center'>
    <img  src='./resources/paquetes1.png'>
    <p>Componente tarjeta creado dentro del paquete components</p>
</div>

* Empezamos con la clase **Component** y como de costumbre vamos a realizar los siguientes pasos:

Como el componente no tiene botones no vamos a necesitar la implementación de ninguna interfaz.
```javascript
public class TarjetaComponent{
}
```

vamos a crear un objeto de la clase **Template** y realizaremos la inyección enviándole por argumento la referencia de si mismo con la palabra **this**:

**Declaración:**
```javascript
private TarjetaTemplate tarjetaTemplate;
```

**Ejemplificación:**
```javascript
// Dentro del constructor
tarjetaTemplate = new TarjetaTemplate(this);
```

Creamos su método **get** de su respectivo **Template**:
```javascript
public TarjetaTemplate getTarjetaTemplate(){
    return tarjetaTemplate;
}
```

* Ahora vamos a codificar nuestra clase **Template**:

Esta al representar las características gráficas del componente debe heredar de un **JPanel**:  
```javascript
public class TarjetaTemplate extends JPanel{
}
```

Como hemos venido haciendo vamos a obtener los servicios **ObjGraficosService** y **RecursosService** ademas de obtener por constructor la inyección de la clase **Component**:

**Ejemplificación**
```javascript
private TarjetaComponent tarjetaComponent;
private ObjGraficosService sObjGraficos;
private RecursosService sRecursos;
```

**Obtención de servicios e inyección:**
```javascript
public TarjetaTemplate(TarjetaComponent tarjetaComponent){

    this.tarjetaComponent = tarjetaComponent;
    sObjGraficos= ObjGraficosService.getService();
    sRecursos = RecursosService.getService();
}
```

Ahora le damos propiedades gráficas a nuestro componente:
```javascript
// Dentro del constructor
this.setSize(256, 230);
this.setBackground(Color.white);
this.setLayout(null);
this.setVisible(true);
```

Note que el tamaño del componente debe ser igual panel que va a reemplazar. En este caso va a reemplazar a los paneles **pMision, pVision y pNosotros**.

<div align='center'>
    <img  src='./resources/codigo3.png'>
    <p>Mismo tamaño del componente con los paneles a reemplazar</p>
</div>

Este componente va a tener:
* Label que contiene la imagen.
* Label que contiene el titulo.
* Label que contiene el párrafo.
* Objeto decorador iDimAux para redimensionar imágenes.

Vamos a realizar su declaración:
```javascript
// Declaración Objetos Gráficos
private JLabel lTitulo, lImagen, lParrafo;

// Declaración Objetos Decoradores
private ImageIcon iDimAux;
```

Como el contenido del titulo, el contenido del párrafo y la imagen van a ser las propiedades que serán dinámicas (que van a variar). Estas debemos recibirlas de alguno modo. Se podría tomar el enfoque de **setters y getters** sin embargo se opta por recibir estos parámetros desde el constructor:

<div align='center'>
    <img  src='./resources/codigo1.png'>
    <p>Parámetros recibidos en el constructor</p>
</div>

Se puede notar que ahora el constructor a parte de recibir la **inyección de dependencia** recibe también ahora:
* Un **String** que representa el titulo.
* Un **ImageIcon** que representa la imagen del componente
* Un **String** que representa el párrafo.

**Nota:** Como el código de este componente es corto y para evitar la declaración de mas atributos en la clase vamos a crear los objetos gráficos dentro del constructor:

**Imagen:**
```javascript
// Dentro del constructor
iDimAux = new ImageIcon(
    iImagen.getImage().getScaledInstance(246, 110, Image.SCALE_AREA_AVERAGING)
);
lImagen= sObjGraficos.construirJLabel(null, 5, 5, 246, 110, iDimAux, null, null, null);
this.add(lImagen);
```
Se puede observar que la imagen que redimensiona la variable auxiliar **iDimAux** es la que fue recibida por el constructor.

**Titulo:**
```javascript
// Dentro del constructor
this.lTitulo = sObjGraficos.construirJLabel(
    titulo, -15, 120, 180, 30, null, sRecursos.getColorAzul(), null, sRecursos.getFontTitulo()
);
this.add(lTitulo);
```

Se puede observar que el texto que se envía para la construcción del Label es el String **titulo** recibido desde el constructor

**Párrafo:**
```javascript
// Dentro del constructor
lParrafo= sObjGraficos.construirJLabel(
    parrafo, 20, 120, 206, 120, null, sRecursos.getColorGrisOscuro(), null, sRecursos.getFontPequeña()
);
this.add(lParrafo);
```
Se puede observar que el texto que se envía para la construcción del Label es el String **parrafo** recibido desde el constructor.

En teoría nuestro componente esta listo, sin embargo ahora el editor de texto nos indica que hay un error en la clase **Component** justamente en la ejemplificación de la clase **Template**, esto es debido a que ahora por constructor esta pidiendo mas parámetros y como argumento solo estamos enviando la **inyección**.

<div align='center'>
    <img  src='./resources/codigo2.png'>
    <p>Error en clase Component ya que se piden mas parámetros</p>
</div>

Para resolver esto vamos a recibir por parámetro en el constructor de la clase **Component** los mismos parámetros (**Titulo, Imagen, Párrafo**), para después pasárselos a su clase **Template**:
```javascript
public TarjetaComponent(
    String titulo, ImageIcon iImagen, String parrafo
){
    tarjetaTemplate = new TarjetaTemplate(
        this, titulo, iImagen, parrafo
    );
}
```

Nuestro componente **Tarjeta** esta listo para ser incorporado, debemos hacer dicha incorporación en el componente **inicio**. Nos posicionamos en la clase **InicioTemplate** y allí primero vamos a declarar las imágenes que necesitamos para nuestros componentes y luego las ejemplificamos:

**Declaración:**
```javascript
private ImageIcon iTarjeta1, iTarjeta2, iTarjeta3;
```

**Ejemplificación:**
```javascript
// Dentro del método crearObjetosDecoradores
this.iTarjeta1 = new ImageIcon("clase7/resources/img/tarjeta1.jpg");
this.iTarjeta2 = new ImageIcon("clase7/resources/img/tarjeta2.jpg");
this.iTarjeta3 = new ImageIcon("clase7/resources/img/tarjeta3.jpg");
```

Note algo importante, aunque es el componente **tarjeta** el que va a mostrar las imágenes en pantalla estas van a ser creadas de su componente padre que es **inicio** en este caso. Ya que el componente **tarjeta** exige unas imágenes como parámetros y estas deben ser creadas previamente. Sin embargo el componente **Tarjeta** si se encarga de su redimensión en las imágenes.

Vamos a realizar la incorporación de este componente primero en el método **crearContenidoPMision**, como este enfoque es **por medio de incorporación** lo que debemos hacer es adicionar el componente al panel que reemplazara:

* Llamamos al panel en cuestión y le indicamos que se añadirá algo con el método **add**:
```javascript
public void crearContenidoPMision(){
    this.pMision.add();
}
```

* Dentro de los paréntesis vamos a crear una **Ejemplificación anónima** del componente:
```javascript
public void crearContenidoPMision(){
    this.pMision.add(new TarjetaComponent());
}
```

* Sin embargo debemos recordar que el la clase **Component** pide por parámetros 3 cosas **(String titulo, ImageIcon imagen, Sitrng párrafo)** asi que debemos enviarle por argumento estas 3 cosas:
```javascript
public void crearContenidoPMision(){
    this.pMision.add(
        new TarjetaComponent(
            "Nuestra Misión", 
            iTarjeta1, 
            "<html><div align='justify'>Brindar cursos a la comunidad académica para complementar habilidades fuera del pensum común.</div></html>" 
        )
    );
}
```
Noten que el texto que enviamos como párrafo tiene etiquetas html para que el texto cuente con saltos de linea y ademas el texto tenga un formato justificado.

* Hasta el momento la incorporación esta fallando y es que debemos recordar que la clase **Component** no cuenta con características gráficas, es la clase **Template** la que las tiene asi que debemos obtenerla mediante el método **get** de la clase **Component**.

```javascript
public void crearContenidoPMision(){
    this.pMision.add(
        new TarjetaComponent(
            "Nuestra Misión", 
            iTarjeta1, 
            "<html><div align='justify'>Brindar cursos a la comunidad académica para complementar habilidades fuera del pensum común.</div></html>" 
        ).getTarjetaTemplate()
    );
}
```

* Ahora el contenido esta listo, solo falta llamar al método **crearContenidoPMision** para que el contenido pueda ser visible:
```javascript
// Dentro del constructor
this.crearObjetosDecoradores();
this.crearContenidoPMision();
```
**Nota:** También faltaba llamar al método **crearObjetosDecoradores** dentro del constructor.

Si corremos nuestra aplicación y oprimimos el botón que muestra el componente **inicio** se vera algo asi:

<div align='center'>
    <img  src='./resources/interfaz6.png'>
    <p>Interfaz gráfica con la llamada del componente tarjeta 1 vez</p>
</div>

Vamos a realizar el mismo proceso con los otros 2 paneles, claramente el contenido sera distinto asi que los argumentos enviados al componente también lo serán:

**Panel pVision:**

```javascript
public void crearContenidoPVision(){
    this.pVision.add(
        new TarjetaComponent(
            "Nuestra Visión", 
            iTarjeta2, 
            "<html><div align='justify'>Brindar cursos académicos al 80% de los estudiantes de ingeniería de Sistemas.</div></html>" 
        ).getTarjetaTemplate()
    );
}
```

**Panel pNosotros:**
```javascript
public void crearContenidoPNosotros(){
    this.pNosotros.add(
        new TarjetaComponent(
            "Sobre Nosotros", 
            iTarjeta3, 
            "<html><div align='justify'>Somos un grupo de trabajo de la Universidad distrital Francisco jose de Caldas.</div></html>"
        ).getTarjetaTemplate()
    );
}
```

Si corremos nuestra aplicación nuevamente ahora se vera asi:

<div align='center'>
    <img  src='./resources/interfaz7.png'>
    <p>Componente inicio con la reutilización del componente Tarjeta</p>
</div>

# Enfoque de reutilización por Posicionamiento.

Muchas veces vamos a querer un componente gráfico muchas veces dentro de nuestro componente padre, un ejemplo puede ser una lista de películas donde cada película va a ser un componente hijo, sin embargo podría ser un listado largo de películas que queremos mostrar y no sabemos cuantas sean. Por lo que crear paneles base para ser reemplazados es una idea, una buena opción es traer el componente gráfico hijo y posicionarlo dentro de un espacio. 

Este enfoque se caracteriza por esto. Cuando creemos el **componente hijo**, este no va a reemplazar nada sino que va a ocupar un espacio dentro del **componente padre** por lo que ahora debemos tener presente la locación de este componente y ya no nos preocupa que el componente hijo tenga el mismo tamaño de algún objeto gráfico ya que al no reemplazar nada esta libre de tener un tamaño independiente.

Vamos a crear un nuevo component que encapsule la creación del contenido de los paneles inferiores, este componente sera llamado **accion**, para esto creamos su respectivo paquete y clases dentro del directorio **components**:

<div align='center'>
    <img  src='./resources/paquetes2.png'>
    <p>Componente accion creado dentro del paquete components</p>
</div>

* Empezamos con la clase **Component** y como de costumbre vamos a realizar los siguientes pasos:

Como el componente no tiene botones no vamos a necesitar la implementación de ninguna interfaz.
```javascript
public class AccionComponent {
}
```

vamos a crear un objeto de la clase **Template** y realizaremos la inyección enviándole por argumento la referencia de si mismo con la palabra **this**:

**Declaración:**
```javascript
private AccionTemplate accionTemplate;
```

**Ejemplificación:**
```javascript
// Dentro del constructor
this.accionTemplate= new AccionTemplate(this);
```

Creamos su método **get** de su respectivo **Template**:
```javascript
public AccionTemplate getAccionTemplate(){
    return accionTemplate;
}
```

* Ahora vamos a codificar nuestra clase **Template**:

Esta al representar las características gráficas del componente debe heredar de un **JPanel**:  
```javascript
public class AccionTemplate extends JPanel {
}
```

Como hemos venido haciendo vamos a obtener los servicios **ObjGraficosService** y **RecursosService** ademas de obtener por constructor la inyección de la clase **Component**:

**Ejemplificación**
```javascript
private AccionComponent accionComponent;
private ObjGraficosService sObjGraficos;
private RecursosService sRecursos;
```

**Obtención de servicios e inyección:**
```javascript
public AccionTemplate(AccionComponent accionComponent){

    this.accionComponent = accionComponent;
    sObjGraficos= ObjGraficosService.getService();
    sRecursos = RecursosService.getService();
}
```

Ahora le damos propiedades gráficas a nuestro componente:
```javascript
// Dentro del constructor
this.setSize(250, 125);
this.setBackground(Color.WHITE);
this.setBorder(sRecursos.getBorderGris());
this.setLayout(null);
this.setVisible(true);
```

Note que el componente tiene un borde que llamamos del servicio **RecursosService** para poder diferenciarse. Ademas el tamaño del componente no debe ser igual a ningún objeto gráfico del componente padre ya que se usara un enfoque de **posicionamiento**.

Este componente va a tener:
* Label que contiene el titulo.
* Label que contiene el icono.
* Label que contiene el párrafo.
* Objeto decorador iDimAux para redimensionar imágenes.

Vamos a realizar su declaración:
```javascript
// Declaración Objetos Gráficos
private JLabel lImagen, lTitulo, lParrafo;

// Declaración Objetos Decoradores
private ImageIcon iDimAux;
```

Como el contenido del titulo, el contenido del párrafo y el icono van a ser las propiedades que serán dinámicas (que van a variar). Estas debemos recibirlas de alguno modo. Nuevamente se opta por recibir estos parámetros desde el constructor:

<div align='center'>
    <img  src='./resources/codigo4.png'>
    <p>Parámetros recibidos en el constructor</p>
</div>

Se puede notar que ahora el constructor a parte de recibir la **inyección de dependencia** recibe también ahora:
* Un **ImageIcon** que representa el icono del componente
* Un **String** que representa el titulo.
* Un **String** que representa el párrafo.

**Nota:** Como el código de este componente también es corto y para evitar la declaración de mas atributos en la clase vamos a crear los objetos gráficos dentro del constructor:

**Imagen:**
```javascript
// Dentro del constructor
iDimAux = new ImageIcon(
    imagen.getImage().getScaledInstance(45, 45, Image.SCALE_AREA_AVERAGING)
);
this.lImagen = sObjGraficos.construirJLabel(null, (250-60)/2, 5, 45, 45, iDimAux, null, null, null);
this.add(lImagen);
```
Se puede observar que la imagen que redimensiona la variable auxiliar **iDimAux** es la que fue recibida por el constructor.

**Titulo:**
```javascript
// Dentro del constructor
this.lTitulo = sObjGraficos.construirJLabel(
    titulo, (250-220)/2, 50, 220, 30, null, sRecursos.getColorGrisOscuro(), null, sRecursos.getFontTitulo()
);
this.add(lTitulo);
```

Se puede observar que el texto que se envía para la construcción del Label es el String **titulo** recibido desde el constructor

**Párrafo:**
```javascript
// Dentro del constructor
this.lParrafo = sObjGraficos.construirJLabel(
    parrafo, (250-230)/2, 75, 230, 50, null, sRecursos.getColorGrisOscuro(), null, sRecursos.getFontPequeña()
);
this.add(lParrafo);
```
Se puede observar que el texto que se envía para la construcción del Label es el String **parrafo** recibido desde el constructor.

Como vimos con nuestro anterior componente **tarjeta** la clase **Component** ahora debe ser modificada ya que la clase **Template** exige nuevos parámetros que deben ser enviados como argumento

Para resolver esto vamos a recibir por parámetro en el constructor de la clase **Component** los mismos parámetros (**Imagen, Titulo, Párrafo**), para después pasárselos a su clase **Template**:
```javascript
public AccionComponent(
    ImageIcon imagen, String titulo, String parrafo
){
    this.accionTemplate= new AccionTemplate(
        this, imagen, titulo, parrafo
    );
}
```

El componente gráfico **Accion** esta listo para usarse ahora vamos a ver de que manera se llama este desde su componente padre para ser utilizado.

Nos posicionamos en la clase **InicioTemplate** y allí primero vamos a declarar las imágenes que necesitamos para nuestros componentes y luego las ejemplificamos:

**Declaración:**
```javascript
private ImageIcon iClase, iPantalla, iIdea, iCelular, iEstadistica, iDireccion; 
```

**Ejemplificación:**
```javascript
// Dentro del método crearObjetosDecoradores
this.iClase = new ImageIcon("clase7/resources/img/clases.png");
this.iPantalla = new ImageIcon("clase7/resources/img/pantalla.png");
this.iCelular = new ImageIcon("clase7/resources/img/celular.png");
this.iIdea = new ImageIcon("clase7/resources/img/ideas.png");
this.iEstadistica = new ImageIcon("clase7/resources/img/estadisticas.png");
this.iDireccion = new ImageIcon("clase7/resources/img/direccion.png");
```
* Previo a la llamada del componente gráfico **Accion** vamos a darle un titulo al componente inferior:
```javascript
public void crearContenidoPAcciones(){
    this.lAcciones = sObjGraficos.construirJLabel(
        "Nuestros Servicios", 10, 10, 160, 30, null, sRecursos.getColorAzul(), null, sRecursos.getFontTitulo()
    );
    this.pAcciones.add(lAcciones);
}
```

Como esta vez no vamos a incorporar ni reemplazar ningún componente no podemos crear la ejemplificación de forma anónima entonces debemos crear variables para representar los objetos:
```javascript
// Dentro del método crearContenidoPAcciones
AccionTemplate p1= new AccionComponent();
```
Debemos recordar:
* la clase **AccionComponent** nos exige el envío de algunos argumentos.
* Debemos obtener la clase **Template** del componente ya que el objeto que queremos crear es de esta clase.

```javascript
// COMPONENTE ACCIÓN 1 ------------------------------------
AccionTemplate p1= new AccionComponent(
    iClase, 
    "Clases", 
    "<html><div align='center'>Clases a la comunidad que complementan el pensum.</div></html>"
).getAccionTemplate();
```

Ya tenemos un objeto del componente hijo dentro de **inicio** pero aun debemos indicarle la posición y la agregación en el panel inferior:
```javascript
p1.setLocation(15, 50);
this.pAcciones.add(p1);
```

Para probar el método lo llamaremos en el constructor:

```javascript
// Dentro del constructor
this.crearContenidoPAcciones();
```

si ejecutamos nuestra aplicación se vera asi:

<div align='center'>
    <img  src='./resources/interfaz8.png'>
    <p>Componente inicio con la agregación de un componente accion</p>
</div>

este proceso lo repetiremos varias veces mas:
```javascript
// COMPONENTE ACCIÓN 2 ------------------------------------
AccionTemplate p2= new AccionComponent(
    iPantalla, 
    "Clases Virtuales", 
    "<html><div align='center'>Cursos virtuales como medio de enseñanza.</div></html>"
).getAccionTemplate();
p2.setLocation(30 + p2.getWidth(), 50);
this.pAcciones.add(p2);

// COMPONENTE ACCIÓN 3 ------------------------------------
AccionTemplate p3= new AccionComponent(
    iIdea, 
    "Generación de ideas", 
    "<html><div align='center'>Desarrollo de ideas con tecnologías actuales.</div></html>"
).getAccionTemplate();
p3.setLocation(45 + p3.getWidth() * 2, 50);
this.pAcciones.add(p3);

// COMPONENTE ACCIÓN 4 ------------------------------------
AccionTemplate p4= new AccionComponent(
    iCelular, 
    "Notificaciones", 
    "<html><div align='center'>Notificaión el estado de tus cursos y actividades.</div></html>"
).getAccionTemplate();
p4.setLocation(15, 65 + p4.getHeight());
this.pAcciones.add(p4);

// COMPONENTE ACCIÓN 5 ------------------------------------
AccionTemplate p5= new AccionComponent(
    iEstadistica, 
    "Estadisticas", 
    "<html><div align='center'>Gestión de participación en nuestros cursos.</div></html>"
).getAccionTemplate();
p5.setLocation(30 + p5.getWidth(), 65 + p5.getHeight());
this.pAcciones.add(p5);

// COMPONENTE ACCIÓN 6 ------------------------------------
AccionTemplate p6= new AccionComponent(
    iDireccion, 
    "Dirección", 
    "<html><div align='center'>Damos direcciónamiento a nuestros estudiantes.</div></html>"
).getAccionTemplate();
p6.setLocation(45 + p6.getWidth() * 2, 65 + p6.getHeight());
this.pAcciones.add(p6);
```

Si corremos nuestra aplicación vamos a ver el resultado que queríamos ver desde el comienzo:

<div align='center'>
    <img  src='./resources/interfaz2.png'>
    <p>Vista Principal con el panel inicio terminado</p>
</div>

El anterior enfoque queda un tanto desperdiciado debido a que repetimos el código las 6 veces que fue reutilizado el componente. Si existieran 10 acciones mas tendríamos que volver a repetir este codigo y no es para nada optimo hacer esto. Un enfoque apropiado es crear un arreglo de objetos donde cada objeto contenga la información necesaria (Imagen, Titulo, Párrafo) y recorrerlo mediante un ciclo para que de esta forma el código solo sea escrito una sola vez y de esta forma ahorrarnos lineas de código. Sin embargo este enfoque se discutirá en futuras clases cuando se hable acerca de **Servicios**.

## Pequeña Reflexión de la reutilización

El concepto de reutilización es quizás el factor que mas le da reconocimiento al uso de los componentes gráficos, de esta forma no solo tenemos un código mucho mas entendible y organizado. También estamos encapsulado la estructura de una plantilla dentro de un componente lo cual dota de nuestro código de una estructuración y modularizacion alta. Incluso el concepto de reutilización puede ser tan util  que si por ejemplo en algún otro componente como puede ser **Productos** quiero usar uno de estos componentes reutilizables perfectamente se puede hacer haciendo de su función mucho mas amplia para nuestro proyecto. 

Otro criterio que podría tomarse a nuestro favor es el dinamismo hacia el tamaño y posición de objetos gráficos dentro de la estructura de un componente altamente reutilizable, piense por un momento, que tal si quiero usar de nuevo el componente **tarjeta** en otra parte de mi proyecto pero lo quiero con mas altura o menos ancho, podría el componente pedir también el ancho y alto por parámetro que necesitemos para incorporarlo o posicionarlo en nuestra interfaz y usar un posicionamiento y tamaño de  los objetos gráficos internos basado en **porcentajes** para que no se pierda la estructura deseada.

Este posicionamiento basado en **porcentajes** es un enfoque que mejora el enfoque de posicionamiento en pixeles pero esta basado en el. En este curso no veremos dicho enfoque pero se menciona para motivar a la investigación al estudiante que cursa por este curso.

# Resultado

Si has llegado hasta aquí **!Felicidades!** has aprendido a crear reutilización de componentes gráficos cuando utilizarlos y los diferentes enfoques de incorporación y posicionamiento para encapsular la estructura de una plantilla en un componente que podemos usar varias veces.

En la siguiente clase vamos a continuar con el tema de **Eventos** y esta vez vamos a estudiar los eventos del **Mouse**.

# Actividad

Utilizar reutilización de componentes en sus proyectos para encapsular la lógica y estructura de una plantilla que pueda utilizarse varias veces.